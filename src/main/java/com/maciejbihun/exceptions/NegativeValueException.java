package com.maciejbihun.exceptions;

public class NegativeValueException extends RuntimeException {

    private static final String VALUE_CAN_NOT_BE_NEGATIVE = "Value can not be negative.";

    public NegativeValueException(){
        super(VALUE_CAN_NOT_BE_NEGATIVE);
    }

}
