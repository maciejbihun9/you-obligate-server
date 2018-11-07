package com.maciejbihun.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author BHN
 */
@Entity
@Table(name = "User_")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "NAME", updatable = true, length = 30)
    private String name;

    @Basic(optional = false)
    @Column(name = "SURNAME", updatable = true, length = 30)
    private String surname;

    @Basic(optional = false)
    @Column(name = "USERNAME", unique = true, updatable = true, length = 30)
    private String username;

    @Basic(optional = false)
    @Column(name = "PASSWORD", updatable = true, length = 60 /* length 60 for BCrypt */)
    private String password;

    @ElementCollection
    private List<String> expectedServicesTerms = new ArrayList<>();

    /**
     * User won't have many UserRegisteredService, so it is ok to load them eagerly.
     */
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<UserRegisteredService> userRegisteredServices = new ArrayList<>();

    // LazyCollection is an annotation to omit exception with fetching multiple bags at the same time
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<UserRegisteredService> getUserRegisteredServices() {
        return userRegisteredServices;
    }

    public void setUserRegisteredServices(List<UserRegisteredService> userRegisteredServices) {
        this.userRegisteredServices = userRegisteredServices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public List<String> getExpectedServicesTerms() {
        return expectedServicesTerms;
    }
}
