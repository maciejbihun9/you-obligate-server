package com.maciejbihun.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "UserUnitsRequest")
public class UserUnitsRequest {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_UNITS_REQUEST_SEQ")
    @SequenceGenerator(name = "USER_UNITS_REQUEST_SEQ", sequenceName = "USER_UNITS_REQUEST_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Basic(optional = false)
    @Column(name = "SERVICE_ID", nullable = false, updatable = false)
    private Long serviceId;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

}