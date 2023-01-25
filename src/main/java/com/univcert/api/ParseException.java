package com.univcert.api;

public class ParseException extends org.json.simple.parser.ParseException {
    public ParseException(int errorType) {
        super(errorType);
    }
}
