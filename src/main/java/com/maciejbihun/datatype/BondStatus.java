package com.maciejbihun.datatype;

/**
 * @author Maciej Bihun
 */
public enum BondStatus {

    CREATED("Created"), PAID("PAID"), CLOSED("Closed"), CANCELLED("Cancelled");

    private String bondStatus;

    BondStatus(String bondStatus){
        this.bondStatus = bondStatus;
    }

    public String getBondStatus() {
        return bondStatus;
    }
}
