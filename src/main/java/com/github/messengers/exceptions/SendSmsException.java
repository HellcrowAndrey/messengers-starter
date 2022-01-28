package com.github.messengers.exceptions;

public class SendSmsException extends RuntimeException {

    public SendSmsException() {
    }

    public SendSmsException(String message) {
        super(message);
    }
}
