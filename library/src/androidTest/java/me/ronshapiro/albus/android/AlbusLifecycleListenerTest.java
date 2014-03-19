package me.ronshapiro.albus.android;

import android.test.AndroidTestCase;

import static me.ronshapiro.albus.android.TestsHelper.getDefaultAlbus;
import static me.ronshapiro.albus.android.TestsHelper.simulateCrash;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlbusLifecycleListenerTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Albus.suppressDefaultExceptionHandler(true);
    }

    public void testCallbacksAreCalled() {
        Albus dumbledore = getDefaultAlbus(this);
        AlbusLifecycleListener mockListener = mock(AlbusLifecycleListener.class);
        dumbledore.addLifecycleListener(mockListener);

        verify(mockListener, never()).onAlbusStarted(any(Storage.class));
        Albus.start(dumbledore);
        verify(mockListener, times(1)).onAlbusStarted(any(Storage.class));

        long initialNewestId = dumbledore.getStorage().getNewestId();
        verify(mockListener, never()).onModulesFinishedSaving(any(Storage.class));
        simulateCrash(new RuntimeException("testing " + getName()));
        verify(mockListener, times(1)).onModulesFinishedSaving(any(Storage.class));
        assertNotSame(initialNewestId + " :: " + dumbledore.getStorage().getNewestId(),
                initialNewestId, dumbledore.getStorage().getNewestId());
    }

    public void testCanAddMultipleListeners() {
        Albus dumbledore = getDefaultAlbus(this);
        AlbusLifecycleListener mock1 = mock(AlbusLifecycleListener.class);
        AlbusLifecycleListener mock2 = mock(AlbusLifecycleListener.class);
        dumbledore.addLifecycleListener(mock1);
        dumbledore.addLifecycleListener(mock2);
        Albus.start(dumbledore);

        simulateCrash(new RuntimeException("testing " + getName()));

        verify(mock1, times(1)).onModulesFinishedSaving(any(Storage.class));
        verify(mock2, times(1)).onModulesFinishedSaving(any(Storage.class));
    }
}
