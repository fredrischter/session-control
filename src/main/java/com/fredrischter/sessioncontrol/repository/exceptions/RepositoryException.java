package com.fredrischter.sessioncontrol.repository.exceptions;

public class RepositoryException extends RuntimeException {

    public RepositoryException(Exception e) {
        super(e);
    }
}
