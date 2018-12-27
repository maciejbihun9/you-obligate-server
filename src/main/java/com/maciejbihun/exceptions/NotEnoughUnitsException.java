package com.maciejbihun.exceptions;

public class NotEnoughUnitsException extends RuntimeException {

    private static final String NOT_ENOUGH_UNITS = "Bond does not have enough units of service.";

    public NotEnoughUnitsException()
    {
        super(NOT_ENOUGH_UNITS);
    }

}
