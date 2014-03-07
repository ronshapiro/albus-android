package me.ronshapiro.albus.android;

import junit.framework.TestCase;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ForwardingLoggerTest extends TestCase {

    public void testForwardingOfMethods() {
        Logger wrapped = mock(Logger.class);
        ForwardingLogger proxy = new ForwardingLogger(wrapped);

        Throwable t = new Throwable();
        
        proxy.d("", "");
        proxy.e("", "");
        proxy.i("", "");
        proxy.v("", "");
        proxy.w("", "");

        proxy.d("", "", t);
        proxy.e("", "", t);
        proxy.i("", "", t);
        proxy.v("", "", t);
        proxy.w("", "", t);

        proxy.w("", t);

        verify(wrapped, times(1)).d(anyString(), anyString());
        verify(wrapped, times(1)).e(anyString(), anyString());
        verify(wrapped, times(1)).i(anyString(), anyString());
        verify(wrapped, times(1)).v(anyString(), anyString());
        verify(wrapped, times(1)).w(anyString(), anyString());

        verify(wrapped, times(1)).d(anyString(), anyString(), any(Throwable.class));
        verify(wrapped, times(1)).e(anyString(), anyString(), any(Throwable.class));
        verify(wrapped, times(1)).i(anyString(), anyString(), any(Throwable.class));
        verify(wrapped, times(1)).v(anyString(), anyString(), any(Throwable.class));
        verify(wrapped, times(1)).w(anyString(), anyString(), any(Throwable.class));

        verify(wrapped, times(1)).w(anyString(), any(Throwable.class));

    }
}
