package me.ronshapiro.albus.android;

import android.content.Context;
import android.content.Intent;

public class PostDataImmediatelyListener implements AlbusLifecycleListener {

    private Context context;

    public PostDataImmediatelyListener(Context context) {
        this.context = context;
    }

    @Override
    public void onAlbusStarted(Storage storage) {
        startService();
    }

    @Override
    public void onModulesFinishedSaving(Storage storage) {
        startService();
    }

    private void startService() {
        context.startService(new Intent(context, AlbusService.class));
    }
}
