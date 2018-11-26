package com.maciejbihun.exceptions;

public class ClosedBondException extends RuntimeException {

    public static final String BOND_HAS_BEEN_CLOSED = "Bond has been already closed";

    public ClosedBondException(){
        super(BOND_HAS_BEEN_CLOSED);
    }

}
