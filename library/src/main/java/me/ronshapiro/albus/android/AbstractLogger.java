package me.ronshapiro.albus.android;

import android.util.Log;

/**
 * Convenience class to make implementing custom {@link Logger}s easy.
 */
public abstract class AbstractLogger implements Logger {

    private static final String INFO_FORMAT = "I/%s\t: $s";
    private static final String WARN_FORMAT = "W/%s\t: $s";
    private static final String ERROR_FORMAT = "E/%s\t: $s";
    private static final String VERBOSE_FORMAT = "V/%s\t: $s";
    private static final String DEBUG_FORMAT = "D/%s\t: $s";

    /**
     * @param fullLogLine a pre-formatted {@link String} to log.
     */
    public abstract void log(String fullLogLine);

    /**
     * @param level corresponding levels of severity. Corresponds to int levels in {@link
     * android.util.Log}
     * @param tag short description of the category of message about to appear
     * @param message message to log
     * @param throwable optional (may be null) {@link Throwable} occuring in the app
     */
    private void log(int level, String tag, String message, Throwable throwable){
        String format = null;
        switch (level) {
            case Log.INFO:
                format = INFO_FORMAT;
                break;
            case Log.WARN:
                format = WARN_FORMAT;
                break;
            case Log.ERROR:
                format = ERROR_FORMAT;
                break;
            case Log.VERBOSE:
                format = VERBOSE_FORMAT;
                break;
            case Log.DEBUG:
                format = DEBUG_FORMAT;
                break;
        }
        log(String.format(format, tag, message));
        if (throwable != null) {
            log(String.format(format, tag, Log.getStackTraceString(throwable)));
        }
    }

    @Override
    public final void i(String tag, String message) {
        log(Log.INFO, tag, message, null);
    }

    @Override
    public final void i(String tag, String message, Throwable throwable) {
        log(Log.INFO, tag, message, throwable);
    }

    @Override
    public final void v(String tag, String message) {
        log(Log.VERBOSE, tag, message, null);
    }

    @Override
    public final void v(String tag, String message, Throwable throwable) {
        log(Log.VERBOSE, tag, message, throwable);
    }

    @Override
    public final void d(String tag, String message) {
        log(Log.DEBUG, tag, message, null);
    }

    @Override
    public final void d(String tag, String message, Throwable throwable) {
        log(Log.DEBUG, tag, message, throwable);
    }

    @Override
    public final void w(String tag, String message) {
        log(Log.WARN, tag, message, null);
    }

    @Override
    public final void w(String tag, String message, Throwable throwable) {
        log(Log.WARN, tag, message, throwable);
    }

    @Override
    public final void w(String tag, Throwable throwable) {
        log(Log.WARN, tag, "", throwable);
    }

    @Override
    public final void e(String tag, String message) {
        log(Log.ERROR, tag, message, null);
    }

    @Override
    public final void e(String tag, String message, Throwable throwable) {
        log(Log.ERROR, tag, message, throwable);
    }
}
