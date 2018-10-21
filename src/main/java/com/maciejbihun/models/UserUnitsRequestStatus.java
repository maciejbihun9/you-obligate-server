package com.maciejbihun.models;

/**
 * @author BHN
 */
public enum UserUnitsRequestStatus {

    VERIFIED("VERIFIED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    NOT_VERIFIED("NOT_VERIFIED");

    private String status;

    public String getStatus() {
        return status;
    }

    UserUnitsRequestStatus(String status){
        this.status = status;
    }

}
