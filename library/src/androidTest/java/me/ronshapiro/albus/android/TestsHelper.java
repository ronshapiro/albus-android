package me.ronshapiro.albus.android;

import android.content.Context;
import android.test.AndroidTestCase;

import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TestsHelper {

    public static void simulateCrash(Throwable throwable) {
        Thread.getDefaultUncaughtExceptionHandler()
              .uncaughtException(Thread.currentThread(), throwable);
    }

    public static Module mockModule() {
        Module mock = mock(Module.class);
        doReturn("mock_module::" + Math.random()).when(mock).getName();
        return mock;
    }

    /* package */ static Albus getDefaultAlbus(Context context, String name){
        return new Albus(new SharedPreferencesStorage(context, "test_" + name),
                new CircularBufferLogger(0));
    }

    /* package */ static Albus getDefaultAlbus(AndroidTestCase testCase) {
        return getDefaultAlbus(testCase.getContext(), testCase.getName());
    }
}
