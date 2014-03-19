package me.ronshapiro.albus.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

public class AlbusTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Abc123");
        setContentView(tv);

        startService(new Intent(this, AlbusService.class));

        new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("testing 123");
            }
        }, 2000);
    }
}
