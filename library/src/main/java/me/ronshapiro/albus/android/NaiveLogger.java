package me.ronshapiro.albus.android;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO don't actually ever use this, just convenient for early dev...
 */
public class NaiveLogger extends AbstractLogger {

    private final List<String> logs;
    private static final int AVERAGE_LOG_LENGTH = 200;

    public NaiveLogger() {
        logs = new LinkedList<String>();
    }

    @Override
    public void log(String fullLogLine) {
        logs.add(fullLogLine);
    }

    @Override
    public String produceLogForReport() {
        StringBuilder builder = new StringBuilder(logs.size() * AVERAGE_LOG_LENGTH);
        for (String log : logs) {
            builder.append(log).append('\n');
        }
        return builder.toString();
    }
}
