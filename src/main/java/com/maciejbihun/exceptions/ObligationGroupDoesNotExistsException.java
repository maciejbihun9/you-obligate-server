package com.maciejbihun.exceptions;

public class ObligationGroupDoesNotExistsException extends Exception {

    public static final String OBLIGATION_DOES_NOT_EXISTS = "Obligation group with given id: %s does not exists.";

    public ObligationGroupDoesNotExistsException(Long id){
        super(String.format(OBLIGATION_DOES_NOT_EXISTS, id));
    }

}
