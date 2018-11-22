package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name="ObligationGroup")
public class ObligationGroup {

    public ObligationGroup(){}

    public ObligationGroup(User owner, String name, String moneyName, String moneyShortcutName, String description) {
        this.owner = owner;
        this.name = name;
        this.moneyName = moneyName;
        this.moneyShortcutName = moneyShortcutName;
        this.description = description;
    }

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

    @Basic(optional = true)
    @Column(name = "CREATED_DATE_TIME", nullable = true, updatable = true)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Convert(converter = AtomicReferenceConverter.class)
    @Column(name = "ACCOUNT_BALANCE", nullable = false, updatable = true, length = 400)
    private final AtomicReference<BigDecimal> accountBalance = new AtomicReference<>(BigDecimal.ZERO);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "obligationGroup")
    private List<UserGroupObligationStrategyForRegisteredService> userGroupObligationStrategyForRegisteredServices = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "obligationGroup")
    private List<UserAccountInObligationGroup> userAccountInObligationGroups = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserGroupObligationStrategyForRegisteredServices() {
        return userGroupObligationStrategyForRegisteredServices;
    }

    public List<UserAccountInObligationGroup> getUserAccountInObligationGroups() {
        return userAccountInObligationGroups;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance.get();
    }

    public BigDecimal addMoneyToAccount(final BigDecimal moneyToCreate){
        return this.accountBalance.updateAndGet(amountOfCreatedMoney -> amountOfCreatedMoney.add(moneyToCreate));
    }


}
