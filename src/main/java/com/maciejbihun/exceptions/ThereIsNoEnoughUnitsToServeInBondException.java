package com.maciejbihun.exceptions;

public class ThereIsNoEnoughUnitsToServeInBondException extends Exception {

    private static String thereIsNoEnoughUnitsToServeInBond = "There is no enough units to serve in a bond";

    public ThereIsNoEnoughUnitsToServeInBondException(){
        super(thereIsNoEnoughUnitsToServeInBond);
    }

}
