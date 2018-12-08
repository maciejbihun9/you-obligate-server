package com.maciejbihun.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name="ServiceTag")
public class ServiceTag extends Tag{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TAG_SEQ")
    @SequenceGenerator(name = "TAG_SEQ", sequenceName = "TAG_SEQ", allocationSize = 1)
    Long id;

    @ManyToMany(mappedBy = "expectedServicesTags")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "userRegisteredServicesTags")
    private Set<UserRegisteredService> userRegisteredServices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<UserRegisteredService> getUserRegisteredServices() {
        return userRegisteredServices;
    }
}
