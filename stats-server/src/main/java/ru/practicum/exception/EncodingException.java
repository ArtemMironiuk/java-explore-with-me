package ru.practicum.exception;

import java.io.UnsupportedEncodingException;

public class EncodingException extends RuntimeException {

    public EncodingException(String s) {
        super(s);
    }
}
