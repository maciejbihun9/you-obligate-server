package com.maciejbihun.exceptions;

public class NotEnoughMoneyException extends RuntimeException {

    private static final String NOT_ENOUGH_MONEY = "Not enough money in the account";

    public NotEnoughMoneyException(){
        super(NOT_ENOUGH_MONEY);
    }

}
