package com.maciejbihun.exceptions;

public class GroupAccountOrObligationStrategyDoesNotExistsException extends Exception {

    private static final String GROUP_ACCOUNT_OR_OBLIGATION_STRATEGY_DOES_NOT_EXISTS = "User group account or obligation strategy with given id does not exists.";

    public GroupAccountOrObligationStrategyDoesNotExistsException(){
        super(GROUP_ACCOUNT_OR_OBLIGATION_STRATEGY_DOES_NOT_EXISTS);
    }

}
