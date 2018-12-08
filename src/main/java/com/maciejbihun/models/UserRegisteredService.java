package com.maciejbihun.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maciej Bihun
 * A service that a user will do for an obligation group.
 */
@Entity
@Table(name="UserRegisteredService")
public class UserRegisteredService {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_REGISTERED_SERVICE_SEQ")
    @SequenceGenerator(name = "USER_REGISTERED_SERVICE_SEQ", sequenceName = "USER_REGISTERED_SERVICE_SEQ", allocationSize = 1)
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Basic(optional = false)
    @Column(name = "SERVICE_NAME", updatable = true, length = 30)
    private String serviceName;

    @Basic(optional = false)
    @Column(name = "SERVICE_CATEGORY")
    @Enumerated(EnumType.STRING)
    private UserRegisteredServiceCategory userRegisteredServiceCategory;

    @Basic(optional = false)
    @Column(name = "SERVICE_DESCRIPTION", updatable = true, length = 100)
    private String serviceDescription;

    @Basic(optional = false)
    @Column(name = "SERVICE_EXPERIENCE_DESCRIPTION", updatable = true, length = 100)
    private String experienceDescription;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "UserRegisteredService_UserRegisteredServiceTerm",
            joinColumns = @JoinColumn(name = "USER_REGISTERED_SERVICE_ID",
                    referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "REGISTERED_SERVICE_TAG_ID",
                    referencedColumnName = "ID"))
    private Set<RegisteredServiceTag> registeredServiceTags = new HashSet<>();

    /**
     * User registered services tags which tags user registered service.
     */
    @ManyToMany
    @JoinTable(
            name = "UserRegisteredServicesTags",
            joinColumns = @JoinColumn(
                    name = "USER_REGISTERED_SERVICE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "SERVICE_TAG_ID", referencedColumnName = "ID"))
    private Set<ServiceTag> userRegisteredServicesTags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userRegisteredService")
    private List<RegisteredServiceObligationStrategy> registeredServiceObligationStrategies = new ArrayList<>();

    public List<RegisteredServiceObligationStrategy> getRegisteredServiceObligationStrategies() {
        return registeredServiceObligationStrategies;
    }

    public void setRegisteredServiceTags(Set<RegisteredServiceTag> registeredServiceTags) {
        this.registeredServiceTags = registeredServiceTags;
    }

    public Set<ServiceTag> getUserRegisteredServicesTags() {
        return userRegisteredServicesTags;
    }

    public Set<RegisteredServiceTag> getRegisteredServiceTags() {
        return registeredServiceTags;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public UserRegisteredServiceCategory getUserRegisteredServiceCategory() {
        return userRegisteredServiceCategory;
    }

    public void setUserRegisteredServiceCategory(UserRegisteredServiceCategory userRegisteredServiceCategory) {
        this.userRegisteredServiceCategory = userRegisteredServiceCategory;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public void setExperienceDescription(String experienceDescription) {
        this.experienceDescription = experienceDescription;
    }

    @Override
    public String toString() {
        return "UserRegisteredService{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", userRegisteredServiceCategory=" + userRegisteredServiceCategory +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", experienceDescription='" + experienceDescription + '\'' +
                '}';
    }
}
