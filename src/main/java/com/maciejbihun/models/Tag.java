package com.maciejbihun.models;

import javax.persistence.*;

/**
 * @author Maciej Bihun
 */
@MappedSuperclass
public class Tag {

    @Basic(optional = false)
    @Column(name = "VALUE", nullable = false, updatable = false)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
