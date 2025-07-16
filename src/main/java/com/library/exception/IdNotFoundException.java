package com.library.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(Long id) {
        super("ID (" + id + ") was not found.");
    }
}
