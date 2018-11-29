package com.maciejbihun.exceptions;

public class BondDoesNotExistsException extends RuntimeException {

    private static final String BOND_DOES_NOT_EXISTS_EXCEPTION = "Bond with given id does not exist";

    public BondDoesNotExistsException(){
        super(BOND_DOES_NOT_EXISTS_EXCEPTION);
    }

}
