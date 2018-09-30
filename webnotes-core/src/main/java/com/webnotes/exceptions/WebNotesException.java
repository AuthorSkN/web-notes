package com.webnotes.exceptions;

public class WebNotesException extends RuntimeException {

    public WebNotesException(){
        super();
    }

    public WebNotesException(String message){
        super(message);
    }
}
