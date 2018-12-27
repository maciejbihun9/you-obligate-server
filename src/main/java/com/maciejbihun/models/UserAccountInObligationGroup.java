package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.exceptions.NotEnoughMoneyException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores user's data associated with given obligation group.
 *
 * TESTED
 *
 * @author Maciej Bihun
 */
@Entity
@Table(name = "UserAccountInObligationGroup")
@NamedEntityGraphs({@NamedEntityGraph(name = "graph.userObligationStrategies", attributeNodes = @NamedAttributeNode("userObligationStrategies"))})
public class UserAccountInObligationGroup implements Serializable {

    public UserAccountInObligationGroup(){}

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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_CREATED_DATE_TIME", updatable = false, nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccountInObligationGroup")
    private List<RegisteredServiceObligationStrategy> userObligationStrategies = new ArrayList<>();

    public synchronized void addMoney(final BigDecimal moneyToCreate){
        accountBalance = accountBalance.add(moneyToCreate);
    }

    public synchronized void subtractMoney(final BigDecimal moneyToSubtract){
        if (accountBalance.compareTo(moneyToSubtract) < 0){
            throw new NotEnoughMoneyException();
        }
        accountBalance = accountBalance.subtract(moneyToSubtract);
    }

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
        return accountBalance.setScale(2, RoundingMode.UP);
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
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
