package com.maciejbihun.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name="ObligationGroup")
public class ObligationGroup {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OBLIGATION_GROUP_SERVICE_SEQ")
    @SequenceGenerator(name = "OBLIGATION_GROUP_SERVICE_SEQ", sequenceName = "OBLIGATION_GROUP_SERVICE_SEQ", allocationSize = 1)
    Long id;

    @Basic(optional = false)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_OWNER_ID", nullable = false, updatable = true)
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
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", nullable = false, updatable = true)
    private AtomicLong amountOfCreatedMoney = new AtomicLong(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_ID")
    private List<UserGroupObligationStrategyForRegisteredService> userGroupObligationStrategyForRegisteredServices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<ObligationGroupAccount> obligationGroupAccounts = new ArrayList<>();

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public Long getId() {
        return id;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserGroupObligationStrategyForRegisteredServices() {
        return userGroupObligationStrategyForRegisteredServices;
    }

    public List<ObligationGroupAccount> getObligationGroupAccounts() {
        return obligationGroupAccounts;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
    }

    public String getMoneyShortcutName() {
        return moneyShortcutName;
    }

    public void setMoneyShortcutName(String moneyShortcutName) {
        this.moneyShortcutName = moneyShortcutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public Long getAmountOfCreatedMoney() {
        return amountOfCreatedMoney.get();
    }

    public Long createMoney(Long moneyToCreate){
        return this.amountOfCreatedMoney.addAndGet(moneyToCreate);
    }


}
