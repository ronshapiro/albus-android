package me.ronshapiro.albus.android;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread.UncaughtExceptionHandler;

/* package */ class AlbusExceptionHandler implements UncaughtExceptionHandler {

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
                // TODO this should be recorded somehow
                throw new RuntimeException(e); // shouldn't happen
            }
        }

        Logger logger = dumbledore.getLogger();
        try {
            aggregateModuleData.put("__logger__", logger.produceLogForReport());
        } catch (JSONException e) {
            throw new RuntimeException(e); // shouldn't happen
        }

        storage.write(storage.getNewestId() + 1, aggregateModuleData);

        for (AlbusLifecycleListener listener : dumbledore.getListeners()) {
            listener.onModulesFinishedSaving(storage);
        }

        if (!Albus.getShouldSuppressDefaultExceptionHandler()) {
            previousUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    }
}