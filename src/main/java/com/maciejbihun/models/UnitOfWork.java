package com.maciejbihun.models;

public enum UnitOfWork {

    HOUR("per hour"), SERVICE("per service"), KM("per km"), MILE("per mile");

    private String unitOfWorkDescription;

    UnitOfWork(String unitOfWorkDescription){
        this.unitOfWorkDescription = unitOfWorkDescription;
    }

    public String getUnitOfWorkDescription(){
        return this.unitOfWorkDescription;
    }

}
