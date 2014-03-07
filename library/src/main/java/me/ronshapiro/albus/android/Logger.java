package me.ronshapiro.albus.android;

/**
 * API to match {@link android.util.Log}
 */
public interface Logger {
    void i(String tag, String message);
    void i(String tag, String message, Throwable throwable);
    void v(String tag, String message);
    void v(String tag, String message, Throwable throwable);
    void d(String tag, String message);
    void d(String tag, String message, Throwable throwable);
    void w(String tag, String message);
    void w(String tag, String message, Throwable throwable);
    void w(String tag, Throwable throwable);
    void e(String tag, String message);
    void e(String tag, String message, Throwable throwable);
    String produceLogForReport();
}
