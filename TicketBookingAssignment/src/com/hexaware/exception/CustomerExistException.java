package com.hexaware.exception;

public class CustomerExistException extends Exception {
    public CustomerExistException(String message) {
        super(message);
    }
}