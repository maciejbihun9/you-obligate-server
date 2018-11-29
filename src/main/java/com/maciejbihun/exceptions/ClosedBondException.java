package com.maciejbihun.exceptions;

public class ClosedBondException extends RuntimeException {

    private static final String BOND_HAS_BEEN_CLOSED = "Bond has been already closed. You can not perform any action on that object.";

    public ClosedBondException(){
        super(BOND_HAS_BEEN_CLOSED);
    }

}
