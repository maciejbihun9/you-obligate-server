package com.maciejbihun.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Maciej Bihun
 * <p>
 *  Stores the data about user associated with given obligation group.
 * </>
 */
@Entity
@Table(name = "ObligationGroupAccount")
public class ObligationGroupAccount {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OBLIGATION_GROUP_ACCOUNT_SEQ")
    @SequenceGenerator(name = "OBLIGATION_GROUP_ACCOUNT_SEQ", sequenceName = "OBLIGATION_GROUP_ACCOUNT_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "USER_ID", updatable = false)
    private User user;

    @Basic(optional = false)
    @Column(name = "ACCOUNT_BALANCE", updatable = true)
    private BigDecimal accountBalance = new BigDecimal("0.00");

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_ID", updatable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_CREATED_DATE_TIME", updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<Bond> bonds;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<UserGroupObligationStrategyForRegisteredService> userObligationStrategies;

    // TODO
    // private userGroupActivities: Array<GroupActivity>:

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public void setBonds(List<Bond> bonds) {
        this.bonds = bonds;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserObligationStrategies() {
        return userObligationStrategies;
    }

    public void setUserObligationStrategies(List<UserGroupObligationStrategyForRegisteredService> userObligationStrategies) {
        this.userObligationStrategies = userObligationStrategies;
    }
}
