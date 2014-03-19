package me.ronshapiro.albus.android;

import android.content.Intent;
import android.os.Looper;
import android.os.SystemClock;
import android.test.ServiceTestCase;
import android.util.Log;

public class AlbusServiceTest extends ServiceTestCase<AlbusService> {

    public AlbusServiceTest() {
        super(AlbusService.class);
    }

    public void testServiceWillLiveLongerThanMainThread() {
        Log.d("Ronservice", "start");
        startService(new Intent(getContext(), AlbusService.class));
        SystemClock.sleep(3000);
        Log.d("Ronservice", "------middle-----");
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //throw new RuntimeException("testing 123");
            }
        });

        SystemClock.sleep(3000);
    }
}
