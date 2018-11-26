package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Maciej Bihun
 * Stores user's data associated with given obligation group.
 */
@Entity
@Table(name = "UserAccountInObligationGroup")
@NamedEntityGraphs({@NamedEntityGraph(name = "graph.accountBonds", attributeNodes = @NamedAttributeNode("bonds")),
                    @NamedEntityGraph(name = "graph.userObligationStrategies", attributeNodes = @NamedAttributeNode("userObligationStrategies"))})
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
    private final AtomicReference<BigDecimal> accountBalance = new AtomicReference<>(BigDecimal.ZERO);

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_CREATED_DATE_TIME", updatable = false, nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccountInObligationGroup")
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
        return accountBalance.get();
    }

    public BigDecimal addMoneyToAccount(final BigDecimal moneyToCreate){
        return this.accountBalance.updateAndGet(amountOfCreatedMoney -> amountOfCreatedMoney.add(moneyToCreate));
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserObligationStrategies() {
        return userObligationStrategies;
    }

}
