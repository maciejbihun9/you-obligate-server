package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.exceptions.NotEnoughMoneyException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Stores user's data associated with given obligation group.
 * @author Maciej Bihun
 */
@Entity
@Table(name = "UserAccountInObligationGroup")
@NamedEntityGraphs({@NamedEntityGraph(name = "graph.userObligationStrategies", attributeNodes = @NamedAttributeNode("userObligationStrategies"))})
public class UserAccountInObligationGroup implements Serializable {

    public UserAccountInObligationGroup(){}

    public UserAccountInObligationGroup(User user, ObligationGroup obligationGroup){
        this.user = user;
        this.obligationGroup = obligationGroup;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OBLIGATION_GROUP_ACCOUNT_SEQ")
    @SequenceGenerator(name = "OBLIGATION_GROUP_ACCOUNT_SEQ", sequenceName = "OBLIGATION_GROUP_ACCOUNT_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @Convert(converter = AtomicReferenceConverter.class)
    @Basic(optional = false)
    @Column(name = "ACCOUNT_BALANCE", length = 400)
    private BigDecimal accountBalance = BigDecimal.ZERO;
    /**
     * Money which are blocked by purchase tokens
     */
    @Convert(converter = AtomicReferenceConverter.class)
    @Basic(optional = false)
    @Column(name = "BLOCKED_MONEY", length = 400)
    private BigDecimal blockedMoney = BigDecimal.ZERO;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_CREATED_DATE_TIME", updatable = false, nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccountInObligationGroup")
    private List<RegisteredServiceObligationStrategy> userObligationStrategies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public synchronized BigDecimal addMoneyToAccount(final BigDecimal moneyToCreate){
        return this.accountBalance.add(moneyToCreate);
    }

    /**
     * Moves money from account balance to blocked money account balance.
     * Returns the amount of blocked money.
     */
    public synchronized BigDecimal blockMoney(BigDecimal moneyToBlock){
        // if we would like to block more money than is in the account balance
        if (moneyToBlock.compareTo(accountBalance) > 0){
            throw new NotEnoughMoneyException();
        }
        // subtract money to block
        this.accountBalance = accountBalance.subtract(moneyToBlock);

        // append money to blocked account balance
        this.blockedMoney = blockedMoney.add(moneyToBlock);
        return this.blockedMoney;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public List<RegisteredServiceObligationStrategy> getUserObligationStrategies() {
        return userObligationStrategies;
    }

}
