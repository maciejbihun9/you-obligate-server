package com.maciejbihun.exceptions;

public class AmountOfUnitsExceededException extends Exception {

    private static final String GROUP_ACCOUNT_OR_OBLIGATION_STRATEGY_DOES_NOT_EXISTS = "User group account or obligation strategy with given id does not exists.";

    public AmountOfUnitsExceededException(){
        super(GROUP_ACCOUNT_OR_OBLIGATION_STRATEGY_DOES_NOT_EXISTS);
    }
}
