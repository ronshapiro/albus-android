package me.ronshapiro.albus.android;

public class CircularBufferLogger extends AbstractLogger {

    private final String[] buffer;
    private int index = 0;

    public CircularBufferLogger() {
        this(200);
    }

    public CircularBufferLogger(int size) {
        buffer = new String[size];
    }

    @Override
    public void log(String fullLogLine) {
        synchronized (this) {
            buffer[index] = fullLogLine;
            index = increment(index);
        }
    }

    @Override
    public String produceLogForReport() {
        synchronized (this) {
            StringBuilder builder = new StringBuilder();
            int iterator = index;
            for (int i = 0; i < buffer.length; i++) {
                builder.append(buffer[iterator]).append('\n');
                iterator = increment(iterator);
            }
            return builder.toString();
        }
    }

    private int increment(int someIndex) {
        someIndex++;
        if (someIndex == buffer.length) {
            someIndex = 0;
        }
        return someIndex;
    }
}
