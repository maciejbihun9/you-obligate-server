package com.maciejbihun.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.models.UserUnitsRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserRegisteredServiceDto {

    private Long id;

    private UserDto userDto;

    private String serviceName;

    private UserRegisteredServiceCategory userRegisteredServiceCategory;

    private String serviceDescription;

    private String experienceDescription;

    private LocalDateTime createdDateTime;

    private List<String> registeredServiceTerms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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

    public List<String> getRegisteredServiceTerms() {
        return registeredServiceTerms;
    }

    public void setRegisteredServiceTerms(List<String> registeredServiceTerms) {
        this.registeredServiceTerms = registeredServiceTerms;
    }
}
