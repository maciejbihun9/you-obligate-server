package com.maciejbihun.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="ObligationGroup")
public class ObligationGroup {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OBLIGATION_GROUP_SERVICE_SEQ")
    @SequenceGenerator(name = "OBLIGATION_GROUP_SERVICE_SEQ", sequenceName = "OBLIGATION_GROUP_SERVICE_SEQ", allocationSize = 1)
    Long id;

    @Basic(optional = false)
    @Column(name = "GROUP_OWNER", nullable = false, updatable = true)
    private User owner;

    @Basic(optional = false)
    @Column(name = "GROUP_NAME", nullable = false, updatable = true)
    private String name;

    @Basic(optional = false)
    @Column(name = "MONEY_NAME", nullable = false, updatable = true)
    private String moneyName;

    @Basic(optional = false)
    @Column(name = "MONEY_SHORTCUT_NAME", nullable = false, updatable = true)
    private String moneyShortcutName;
    // private Image image;

    @Basic(optional = false)
    @Column(name = "GROUP_DESCRIPTION", nullable = false, updatable = true)
    private String description;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = true)
    private LocalDateTime createdDateTime;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", nullable = false, updatable = true)
    private Long amountOfCreatedMoney = 0L;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "obligationGroups_registeredServices",
            joinColumns = @JoinColumn(
                    name = "OBLIGATION_GROUP_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(
                    name = "USER_REGISTERED_SERVICE_ID", referencedColumnName = "ID"))
    private List<UserRegisteredService> registeredServices;

}
