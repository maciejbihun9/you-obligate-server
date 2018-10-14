package com.maciejbihun.models;

/**
 * Represents a category that a user is able to pick during registering a service.
 */
public enum UserRegisteredServiceCategory {

    IT("IT"),
    LEARNING("LEARNING"),
    HEALTH("HEALTH"),
    SEX("SEX");

    private String category;

    public String getCategory() {
        return category;
    }

    UserRegisteredServiceCategory(String category){
        this.category = category;
    }

}
