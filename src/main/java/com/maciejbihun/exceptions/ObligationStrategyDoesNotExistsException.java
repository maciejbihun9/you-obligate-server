package com.maciejbihun.exceptions;

public class ObligationStrategyDoesNotExistsException extends Exception {

    private static final String OBLIGATION_STRATEGY_DOES_NOT_EXISTS = "Obligation strategy with given id does not exists.";

    public ObligationStrategyDoesNotExistsException(){
        super(OBLIGATION_STRATEGY_DOES_NOT_EXISTS);
    }

}
