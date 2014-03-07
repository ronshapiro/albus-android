package me.ronshapiro.albus.android;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

import static me.ronshapiro.albus.android.TestsHelper.simulateCrash;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlbusTests extends AndroidTestCase {

    public void setUp() throws Exception {
        Albus.suppressDefaultExceptionHandler(true);
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCrashWritesToModule() {
        Albus testAlbus = new Albus(new MapStorage(new HashMap<Long, JSONObject>()));
        Module module = mock(Module.class);
        testAlbus.addModule(module);

        Albus.start(testAlbus);
        Throwable throwable = new IllegalStateException("Albus test - " + getName());
        simulateCrash(throwable);
        verify(module).record(Thread.currentThread(), throwable);
    }

    public void testCanAddMultipleModules() {
        Albus testAlbus = new Albus(new MapStorage(new HashMap<Long, JSONObject>()));
        Module m1 = mock(Module.class);
        Module m2 = mock(Module.class);
        testAlbus.addModule(m1);
        testAlbus.addModule(m2);
        Albus.start(testAlbus);

        Throwable throwable = new IllegalStateException("Albus test - " + getName());
        simulateCrash(throwable);
        verify(m1).record(Thread.currentThread(), throwable);
        verify(m2).record(Thread.currentThread(), throwable);
    }

    public void testCanSaveMultipleReports() {
        Map<Long, JSONObject> map = new HashMap<Long, JSONObject>();
        Albus testAlbus = new Albus(new MapStorage(map));
        testAlbus.addModule(new Module() {
            @Override
            public String getName() {
                return "test.module1";
            }

            @Override
            public JSONObject record(Thread thread, Throwable throwable) {
                try {
                    return new JSONObject().put("thread", thread.toString())
                            .put("throwable", throwable);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Albus.start(testAlbus);

        Throwable t1 = new IllegalStateException("test abc");
        Throwable t2 = new OutOfMemoryError("test def");
        simulateCrash(t1);
        simulateCrash(t2);
        assertNotNull(map.get(1l));
        assertNotNull(map.get(2l));
        assertEquals(2, map.size());
        assertNotSame(map.get(1l), map.get(2l));
    }

    public void testMultipleModulesGetSavedTogether() {
        Module m1 = mock(Module.class);
        Module m2 = mock(Module.class);
        when(m1.getName()).thenReturn("test.m1");
        when(m2.getName()).thenReturn("test.m2");
        Thread thread = Thread.currentThread();
        Throwable throwable = new CloneNotSupportedException("blah");
        try {
            when(m1.record(thread, throwable)).thenReturn(new JSONObject("{\"a\":\"b\"}"));
            when(m2.record(thread, throwable)).thenReturn(new JSONObject("{\"c\":\"d\"}"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Storage storage = new MapStorage(new HashMap<Long, JSONObject>());
        Albus testAlbus = new Albus(storage);
        testAlbus.addModule(m1);
        testAlbus.addModule(m2);
        Albus.start(testAlbus);

        simulateCrash(throwable);
        assertEquals("{\"test.m1\":{\"a\":\"b\"},\"test.m2\":{\"c\":\"d\"}}",
                storage.read(storage.getNewestId()).toString());
    }

    public void testLoggerDataGetsAddedToStorage() {

    }

    public void testPreviousExceptionHandlerIsCalled() {
        Albus.suppressDefaultExceptionHandler(false);
        UncaughtExceptionHandler handler = mock(UncaughtExceptionHandler.class);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        Albus.start(getContext());

        simulateCrash(new ClassFormatError("class fmt test"));
        verify(handler).uncaughtException(any(Thread.class), any(Throwable.class));
    }
}
