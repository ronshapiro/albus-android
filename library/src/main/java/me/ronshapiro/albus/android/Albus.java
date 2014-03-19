package me.ronshapiro.albus.android;

import android.content.Context;

import java.util.Collection;
import java.util.LinkedList;

import me.ronshapiro.albus.android.api.AlbusApiClient;

public final class Albus {

    private static final String DEFAULT_STORAGE_FILE = "default-albus-storage";
    private static boolean suppressDefaultExceptionHandler = false;
    private static Albus INSTANCE;

    private final Storage storage;
    private final Logger logger;

    private final AlbusApiClient mApiClient;
    private final Collection<Module> modules;
    private final Collection<AlbusLifecycleListener> listeners;

    public static synchronized Albus get() {
        return INSTANCE;
    }

    private static synchronized void set(Albus dumbledore) {
        INSTANCE = dumbledore;
    }

    /**
     * Default entry point for starting Albus. No customization is done.
     * @param context
     */
    public static void start(Context context) {
        Albus dumbledore = new Albus(new SharedPreferencesStorage(context, DEFAULT_STORAGE_FILE));
        dumbledore.addModule(new MainStatsModule(context));
        set(dumbledore);
        dumbledore.addLifecycleListener(new PostDataImmediatelyListener(context));
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
        for (AlbusLifecycleListener listener : INSTANCE.listeners) {
            listener.onAlbusStarted(INSTANCE.storage);
        }
    }

    public static void suppressDefaultExceptionHandler(boolean suppress) {
        suppressDefaultExceptionHandler = suppress;
    }

    public static boolean getShouldSuppressDefaultExceptionHandler() {
        return suppressDefaultExceptionHandler;
    }

    public Albus(Storage storage) {
        this(storage, new CircularBufferLogger());
    }

    public Albus(Storage storage, Logger logger) {
        this.storage = storage;
        this.logger = logger;
        modules = new LinkedList<Module>();
        listeners = new LinkedList<AlbusLifecycleListener>();
        mApiClient = new AlbusApiClient();
    }

    /**
     * don't expose {@link #modules}; if the modules are needed at some point, make sure to clone
     * the collection first.
     */
    /* package */ Collection<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        if (module.getName() == null) {
            throw new IllegalArgumentException(
                    "Module [" + module.getClass().getSimpleName() + "] name must not be null");
        }
        modules.add(module);
    }

    public Storage getStorage() {
        return storage;
    }

    public Logger getLogger() {
        return logger;
    }

    public AlbusApiClient getApiClient() {
        return mApiClient;
    }

    public void addLifecycleListener(AlbusLifecycleListener listener) {
        listeners.add(listener);
    }

    /**
     * don't expose {@link #listeners}; if the modules are needed at some point, make sure to clone
     * the collection first.
     */
    /* package */ Collection<AlbusLifecycleListener> getListeners() {
        return listeners;
    }
}
