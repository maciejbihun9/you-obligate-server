package com.maciejbihun.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Maciej Bihun
 * <p>
 *  Stores the data about user associated with given obligation group.
 * </>
 */
@Entity
@Table(name = "ObligationGroupAccount")
public class ObligationGroupAccount {

    private ObligationGroupAccount(){}

    public ObligationGroupAccount(User user, ObligationGroup obligationGroup){
        this.user = user;
        this.obligationGroup = obligationGroup;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OBLIGATION_GROUP_ACCOUNT_SEQ")
    @SequenceGenerator(name = "OBLIGATION_GROUP_ACCOUNT_SEQ", sequenceName = "OBLIGATION_GROUP_ACCOUNT_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID", nullable = false)
    private User user;

    @Basic(optional = false)
    @Column(name = "ACCOUNT_BALANCE", updatable = true)
    private BigDecimal accountBalance = new BigDecimal("0.00");

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_CREATED_DATE_TIME", updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<Bond> bonds = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID")
    private List<UserGroupObligationStrategyForRegisteredService> userObligationStrategies = new ArrayList<>();

    // TODO
    // private userGroupActivities: Array<GroupActivity>:

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserObligationStrategies() {
        return userObligationStrategies;
    }

}
