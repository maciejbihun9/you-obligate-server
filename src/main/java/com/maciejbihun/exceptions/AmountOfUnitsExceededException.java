package com.maciejbihun.exceptions;

public class AmountOfUnitsExceededException extends Exception {

    private static final String AMOUNT_OF_UNITS_LIMIT_HAS_BEEB_REACHED = "You have reached the limit of created amount of units.";

    public AmountOfUnitsExceededException(){
        super(AMOUNT_OF_UNITS_LIMIT_HAS_BEEB_REACHED);
    }
}
