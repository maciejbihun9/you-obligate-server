package com.maciejbihun.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

/**
 * @author BHN
 */
@Entity
@Table(name = "User_")
@NamedEntityGraphs({@NamedEntityGraph(name = "graph.userRegisteredServices", attributeNodes = @NamedAttributeNode("userRegisteredServices"))})
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

    /**
     * User won't have many UserRegisteredService, so it is ok to load them eagerly.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private List<UserRegisteredService> userRegisteredServices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<UserAccountInObligationGroup> userAccountInObligationGroups = new ArrayList<>();

    /**
     * Expected services tags that describes what user wants in exchange for his services.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "UserExpectedServicesTags",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "SERVICE_TAG_ID", referencedColumnName = "ID"))
    private Set<ServiceTag> expectedServicesTags = new HashSet<>();

    // LazyCollection is an annotation to omit exception with fetching multiple bags at the same time
    @ManyToMany
    @JoinTable(
            name = "Users_Roles",
            joinColumns = @JoinColumn(
                    name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE_ID", referencedColumnName = "ID"))
    private Collection<Role> roles;

    public Set<ServiceTag> getExpectedServicesTags() {
        return expectedServicesTags;
    }

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

    public List<UserAccountInObligationGroup> getUserAccountInObligationGroups() {
        return userAccountInObligationGroups;
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

}
