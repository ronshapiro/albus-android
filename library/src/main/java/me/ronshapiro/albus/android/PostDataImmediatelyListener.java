package me.ronshapiro.albus.android;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import static me.ronshapiro.albus.android.BuildConfig.DEBUG;

public class PostDataImmediatelyListener implements AlbusLifecycleListener {

    private static final String TAG = PostDataImmediatelyListener.class.getSimpleName();

    private Context context;

    public PostDataImmediatelyListener(Context context) {
        this.context = context;
    }

    @Override
    public void onAlbusStarted(Storage storage) {
        if (DEBUG) Log.d(TAG, "onAlbusStarted");
        startService();
    }

    @Override
    public void onModulesFinishedSaving(Storage storage) {
        if (DEBUG) Log.d(TAG, "onModulesFinishedSaving");
        startService();
    }

    private void startService() {
        context.startService(new Intent(context, AlbusService.class));
    }
}
