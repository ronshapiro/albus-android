package me.ronshapiro.albus.android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AlbusService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Ronservice", "command");
        super.onStartCommand(intent, flags, startId);
        new Thread(){
            @Override
            public void run() {
                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Ronservice", "running");
                    }
                }, 1, 100, TimeUnit.MILLISECONDS);
            }
        }.start();
        return START_STICKY;
    }
}
