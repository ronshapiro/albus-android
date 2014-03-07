package me.ronshapiro.albus.android;

public interface AlbusLifecycleListener {
    void onAlbusStarted(Storage storage);
    void onModulesFinishedSaving(Storage storage);
}