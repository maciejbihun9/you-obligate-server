package com.maciejbihun.exceptions;

public class BondWithDiscountCanNotHaveNegativeInterestRateException extends RuntimeException {

    private static final String BOND_WITH_DISCOUNT_CAN_NOT_HAVE_NEGATIVE_INTEREST_RATE = "Discount bond can not have negative interest rate.";

    public BondWithDiscountCanNotHaveNegativeInterestRateException(){
        super(BOND_WITH_DISCOUNT_CAN_NOT_HAVE_NEGATIVE_INTEREST_RATE);
    }

}
