package com.webnotes.exceptions;

public final class WebNotesExceptionFactory {

    private static final String CONNECTIVITY_EXCEPTION = "Database connectivity error.";

    public static WebNotesException createConnectivityException() {
        return new WebNotesException(CONNECTIVITY_EXCEPTION);
    }


}
