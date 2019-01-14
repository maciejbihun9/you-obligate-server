package com.maciejbihun.exceptions;

public class ThereIsNoEnoughMoneyInAnAccountException extends Exception {

    private static String thereIsNoEnoughMoneyInAnAccount = "There is no enough money in an user account";

    public ThereIsNoEnoughMoneyInAnAccountException(){
        super(thereIsNoEnoughMoneyInAnAccount);
    }

}
