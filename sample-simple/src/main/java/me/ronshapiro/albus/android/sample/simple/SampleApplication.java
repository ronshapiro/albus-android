package me.ronshapiro.albus.android.sample.simple;

import android.app.Application;

import me.ronshapiro.albus.android.Albus;
import me.ronshapiro.albus.android.SharedPreferencesStorage;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Albus.start(this);
    }
}
