package me.ronshapiro.albus.android;

import org.json.JSONObject;

public interface Module {
    /**
     * @return an identifiable key for the module; this is used on the backend to determine what
     * handler should process this data - it should be unique, like a package name.
     */
    String getName();
    JSONObject record(Thread thread, Throwable throwable);
}
