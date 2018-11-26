package com.maciejbihun.exceptions;

public class EmptyConstructorIsNotAvailableException extends RuntimeException {

    private static final String EMPTY_CONSTRUCTOR_IS_NOT_AVAILABLE = "Empty constructor is not available. It is public only for Hibernate.";

    public EmptyConstructorIsNotAvailableException(){
        super(EMPTY_CONSTRUCTOR_IS_NOT_AVAILABLE);
    }

}
