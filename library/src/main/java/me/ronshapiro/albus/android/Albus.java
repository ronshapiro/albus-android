package me.ronshapiro.albus.android;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Albus {

    private static final String DEFAULT_STORAGE_FILE = "default-albus-storage";
    private static boolean suppressDefaultExceptionHandler = false;
    private static Albus INSTANCE;
    private final Storage storage;
    private List<Module> modules;

    public static synchronized Albus get() {
        return INSTANCE;
    }

    private static synchronized void set(Albus dumbledore) {
        INSTANCE = dumbledore;
    }

    public static void start(Context context) {
        Albus dumbledore = new Albus(new SharedPreferencesStorage(context, DEFAULT_STORAGE_FILE));
        dumbledore.addModule(new MainStatsModule(context));
        set(dumbledore);
        start();
    }

    /**
     * Start with custom {@link Albus} - no modules will be preconfigured
     */
    public static void start(Albus albus) {
        set(albus);
        start();
    }

    private static void start() {
        Thread.setDefaultUncaughtExceptionHandler(new AlbusExceptionHandler());
    }

    public static void suppressDefaultExceptionHandler(boolean suppress) {
        suppressDefaultExceptionHandler = suppress;
    }

    public static boolean getShouldSuppressDefaultExceptionHandler() {
        return suppressDefaultExceptionHandler;
    }

    public Albus(Storage storage) {
        this.storage = storage;
        modules = new ArrayList<Module>();
    }

    /**
     * don't expose {@link #modules}; if the modules are needed at some point, make sure to clone
     * the collection first.
     */
    /* package */ Collection<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public Storage getStorage() {
        return storage;
    }

    public static interface Module {
        /**
         * @return an identifiable key for the module; this is used on the backend to determine what
         * handler should process this data - it should be unique, like a package name.
         */
        String getName();
        JSONObject record(Thread thread, Throwable throwable);
    }
}
