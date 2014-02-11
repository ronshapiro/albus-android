package me.ronshapiro.albus.android;

public class TestsHelper {

    public static void simulateCrash(Throwable throwable) {
        Thread.getDefaultUncaughtExceptionHandler()
              .uncaughtException(Thread.currentThread(), throwable);
    }
}
