package me.ronshapiro.albus.android;

public class ForwardingLogger implements Logger {

    private Logger delegate;

    /**
     * A delegating {@link Logger} which forwards all method calls to the instance that it wraps.
     * See Effective Java (Second Edition) - Item 16: Favor Composition over Inheritance.
     *
     * @param wrapped logger to delegate to
     */
    public ForwardingLogger(Logger wrapped) {
        delegate = wrapped;
    }

    @Override
    public void i(String tag, String message) {
        delegate.i(tag, message);
    }

    @Override
    public void i(String tag, String message, Throwable throwable) {
        delegate.i(tag, message, throwable);
    }

    @Override
    public void v(String tag, String message) {
        delegate.v(tag, message);
    }

    @Override
    public void v(String tag, String message, Throwable throwable) {
        delegate.v(tag, message, throwable);
    }

    @Override
    public void d(String tag, String message) {
        delegate.d(tag, message);
    }

    @Override
    public void d(String tag, String message, Throwable throwable) {
        delegate.d(tag, message, throwable);
    }

    @Override
    public void w(String tag, String message) {
        delegate.w(tag, message);
    }

    @Override
    public void w(String tag, String message, Throwable throwable) {
        delegate.w(tag, message, throwable);
    }

    @Override
    public void w(String tag, Throwable throwable) {
        delegate.w(tag, throwable);
    }

    @Override
    public void e(String tag, String message) {
        delegate.e(tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        delegate.e(tag, message, throwable);
    }

    @Override
    public String produceLogForReport() {
        return null;
    }
}
