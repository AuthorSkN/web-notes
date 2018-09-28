package com.webnotes.exceptions;

public class WebNotesExceptionFactory {

    private static final String CONNECTIVITY_EXCEPTION = "Database connectivity error.";

    public static WebNotesException createConnectivityException() {
        return new WebNotesException(CONNECTIVITY_EXCEPTION);
    }


}
