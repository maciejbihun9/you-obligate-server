package com.maciejbihun.exceptions;

public class BondNotFoundException extends RuntimeException {

    private static final String BOND_NOT_FOUND_EXCEPTION = "Bond with given id was not found.";

    public BondNotFoundException(){
        super(BOND_NOT_FOUND_EXCEPTION);
    }

}
