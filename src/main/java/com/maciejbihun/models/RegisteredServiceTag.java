package com.maciejbihun.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Informs about different registered services categories.
 * @author Maciej Bihun
 */
@Entity
@Table(name = "RegisteredServiceTag")
public class RegisteredServiceTag {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BOND_SEQ")
    @SequenceGenerator(name = "BOND_SEQ", sequenceName = "BOND_SEQ", allocationSize = 1)
    private Long id;

    /**
     * Field value is not updatable. A user might remove a tag, but he can not change it.
     */
    @Basic(optional = false)
    @Column(name = "VALUE", updatable = false, nullable = false)
    private String value;

    @ManyToMany(mappedBy = "registeredServiceTags")
    private Set<UserRegisteredService> userRegisteredServices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Set<UserRegisteredService> getUserRegisteredServices() {
        return userRegisteredServices;
    }

}
