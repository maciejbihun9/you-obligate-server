package com.maciejbihun.datatype;

/**
 * @author Maciej Bihun
 */
public enum BondStatus {

    CREATED("Created"), PAID("Paid"), LACK_OF_UNITS("Lack of units"), CLOSED("Closed"), CANCELLED("Cancelled");

    private String bondStatus;

    BondStatus(String bondStatus){
        this.bondStatus = bondStatus;
    }

    public String getBondStatus() {
        return bondStatus;
    }
}
