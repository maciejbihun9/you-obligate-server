package com.maciejbihun.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author BHN
 */
@Entity
@Table(name = "UserUnitsRequest")
public class UserUnitsRequest {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_UNITS_REQUEST_SEQ")
    @SequenceGenerator(name = "USER_UNITS_REQUEST_SEQ", sequenceName = "USER_UNITS_REQUEST_SEQ", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_REGISTERED_SERVICE_ID")
    private UserRegisteredService userRegisteredServiceId;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    public Long getId() {
        return id;
    }

    public UserRegisteredService getUserRegisteredServiceId() {
        return userRegisteredServiceId;
    }

    public void setUserRegisteredServiceId(UserRegisteredService userRegisteredServiceId) {
        this.userRegisteredServiceId = userRegisteredServiceId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
