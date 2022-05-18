package com.company.common.exceptions;

public class FileErrorException extends RuntimeException{
    public FileErrorException(String message) {
        super(message);
    }
}
