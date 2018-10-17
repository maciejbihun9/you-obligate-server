package com.maciejbihun.models;

import javax.persistence.*;

@Entity
@Table(name="UserRegisteredService")
public class UserRegisteredService {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_REGISTERED_SERVICE_SEQ")
    @SequenceGenerator(name = "USER_REGISTERED_SERVICE_SEQ", sequenceName = "USER_REGISTERED_SERVICE_SEQ", allocationSize = 1)
    private Long id;

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

    public Long getId() {
        return id;
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
