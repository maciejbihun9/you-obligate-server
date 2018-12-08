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

}
