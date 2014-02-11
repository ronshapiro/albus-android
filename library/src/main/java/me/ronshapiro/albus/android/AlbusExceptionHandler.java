package me.ronshapiro.albus.android;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread.UncaughtExceptionHandler;

import me.ronshapiro.albus.android.Albus.Module;

public class AlbusExceptionHandler implements UncaughtExceptionHandler {

    private final UncaughtExceptionHandler previousUncaughtExceptionHandler;

    public AlbusExceptionHandler() {
        previousUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread thread, Throwable throwable) {
        Albus dumbledore = Albus.get();
        Storage storage = dumbledore.getStorage();
        JSONObject aggregateModuleData = new JSONObject();
        for (Module module : dumbledore.getModules()) {
            try {
                aggregateModuleData.put(module.getName(), module.record(thread, throwable));
            } catch (JSONException e) {
                throw new RuntimeException(e); // shouldn't happen
            }
        }
        storage.write(storage.getNewestId() + 1, aggregateModuleData);

        if (!Albus.getShouldSuppressDefaultExceptionHandler()) {
            previousUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    }


}