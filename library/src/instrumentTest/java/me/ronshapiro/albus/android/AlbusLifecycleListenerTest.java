package me.ronshapiro.albus.android;

import android.test.AndroidTestCase;

import static me.ronshapiro.albus.android.TestsHelper.simulateCrash;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlbusLifecycleListenerTest extends AndroidTestCase {

    public void test_onAlbusCreated_isCalled() {
        Albus.suppressDefaultExceptionHandler(true);
        Albus dumbledore = new Albus(
                new SharedPreferencesStorage(getContext(), "test_" + getName()),
                new CircularBufferLogger(0));
        AlbusLifecycleListener mockListener = mock(AlbusLifecycleListener.class);
        dumbledore.addLifecycleListener(mockListener);

        verify(mockListener, never()).onAlbusStarted(any(Storage.class));
        Albus.start(dumbledore);
        verify(mockListener, times(1)).onAlbusStarted(any(Storage.class));

        long initialNewestId = dumbledore.getStorage().getNewestId();
        verify(mockListener, never()).onModulesFinishedSaving(any(Storage.class));
        simulateCrash(new RuntimeException("testing"));
        verify(mockListener, times(1)).onModulesFinishedSaving(any(Storage.class));
        assertNotSame(initialNewestId + " :: " + dumbledore.getStorage().getNewestId(),
                initialNewestId, dumbledore.getStorage().getNewestId());
    }

    public void test_onModulesFinishedSaving_isCalled() {

    }
}
