package me.ronshapiro.albus.android.sample.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.ronshapiro.albus.android.Albus;

public class MainActivity extends ActionBarActivity {

    private int mCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.countdown);
        textView.setText("Starting");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("AlbusTest", "Running");
                        mCounter--;
                        textView.setText("App will self-destruct in " + mCounter + "...");
                        if (mCounter == 0) {
                            Log.d("AlbusTest", "CRASH!");
                            throw new IllegalStateException("App should crash!");
                        }
                    }
                });
            }
        }, 10, 100, TimeUnit.MILLISECONDS);
    }
}
