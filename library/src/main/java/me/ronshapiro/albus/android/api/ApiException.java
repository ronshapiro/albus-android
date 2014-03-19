package me.ronshapiro.albus.android.api;

import java.io.IOException;

/* package */ class ApiException extends IOException {


    ApiException(String detailMessage) {
        super(detailMessage);
    }

    ApiException(String message, Throwable cause) {
        this(message); // Parent constructor may be used in API > 9
        initCause(cause);
    }

    ApiException(Throwable cause) {
        super(); // Parent constructor may be used in API > 9
        initCause(cause);
    }
}
