package com.example.practice.exception;

public class CustomIOException extends Exception{

    private String message;

    public CustomIOException(String message) {
        this.message = message;
    }
}
