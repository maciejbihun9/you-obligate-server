package com.maciejbihun.models;

import javax.persistence.*;

/**
 * @author BHN
 */
@Entity
@Table(name = "Privilege")
public class Privilege {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRIVILEGE_SEQ")
    @SequenceGenerator(name = "PRIVILEGE_SEQ", sequenceName = "PRIVILEGE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
