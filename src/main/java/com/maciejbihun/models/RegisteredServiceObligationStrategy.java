package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.exceptions.NegativeValueException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents obligation strategy between the user registered service and concrete obligation group.
 * <p>
 * Group contains accounts Accounts contains obligation strategies.
 * This is a contract between a user and obligation group. Strategy is immutable.
 * Each change has to be reflected with another, fresh strategy instance.
 * This approach assures that a strategy will be coherent with a service.
 *
 * @author Maciej Bihun
 */
@Entity
@Table(name="RegisteredServiceObligationStrategy")
public class RegisteredServiceObligationStrategy {

    private static final String NOT_ACCEPTED_AMOUNT_OF_UNITS_PER_BOND = "Minimal amount of units per bond should be grater than 1";

    private static final String NOT_ACCEPTED_INTEREST_RATE_VALUE = "Interest rate should be grater than 0.00";

    public RegisteredServiceObligationStrategy(){}

    public RegisteredServiceObligationStrategy(UserRegisteredService userRegisteredService,
                                               UserAccountInObligationGroup userAccountInObligationGroup,
                                               UnitOfWork unitOfWork,
                                               BigDecimal unitOfWorkCost,
                                               BigDecimal interestRate,
                                               Integer minAmountOfUnitsPerBond,
                                               Integer maxAmountOfUnitsAvailableToCreate) {

        if (unitOfWorkCost.compareTo(BigDecimal.ZERO) < 0 || interestRate.compareTo(BigDecimal.ZERO) < 0 || maxAmountOfUnitsAvailableToCreate < 0){
            throw new NegativeValueException();
        }

        this.userRegisteredService = userRegisteredService;
        this.userAccountInObligationGroup = userAccountInObligationGroup;
        this.unitOfWork = unitOfWork;
        this.unitOfWorkCost = unitOfWorkCost;
        this.interestRate = interestRate;
        this.minAmountOfUnitsPerBond = minAmountOfUnitsPerBond;
        this.maxAmountOfUnitsForObligation = maxAmountOfUnitsAvailableToCreate;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ")
    @SequenceGenerator(name = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ",
            sequenceName = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_REGISTERED_SERVICE_ID", nullable = false)
    private UserRegisteredService userRegisteredService;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ACCOUNT_IN_OBLIGATION_GROUP_ID", nullable = false)
    private UserAccountInObligationGroup userAccountInObligationGroup;

    /**
     * Represents one unit of work type.
     */
    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private UnitOfWork unitOfWork;

    /**
     * Represents how much money a user receives for one unit of work.
     */
    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK_COST", nullable = false, updatable = true)
    private BigDecimal unitOfWorkCost;

    /**
     * Represents the interest rate on the debt. It might be changed by group of users any time.
     */
    @Basic(optional = false)
    @Column(name = "INTEREST_RATE", nullable = false, updatable = true)
    private BigDecimal interestRate = new BigDecimal("0.00");

    /**
     * Represents already created amount of money in given group based on obligated amount of work.
     */
    @Convert(converter = AtomicReferenceConverter.class)
    @Basic(optional = false)
    @Column(name = "ALREADY_CREATED_AMOUNT_OF_MONEY", nullable = false, updatable = true, length = 400)
    private AtomicReference<BigDecimal> alreadyCreatedAmountOfMoney = new AtomicReference<>(BigDecimal.ZERO);

    /**
     * Represents already obligated units of work that needs to be paid back.
     */
    @Basic(optional = false)
    @Column(name = "ALREADY_OBLIGATED_UNITS_OF_WORK", nullable = false, updatable = true)
    private Integer alreadyObligatedUnitsOfWork = 0;

    /**
     * Represents amount of units that user already has paid in given group.
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_UNITS_EVER_PAID", nullable = false, updatable = true)
    private Integer amountOfUnitsEverPaid = 0;

    /**
     * Each bond has to have minimal amount of units. The default value is 1.
     */
    @Basic(optional = false)
    @Column(name = "MIN_AMOUNT_OF_UNITS_PER_BOND", nullable = false, updatable = true)
    private Integer minAmountOfUnitsPerBond;

    /**
     * How many units a user might obligate at once.
     */
    @Basic(optional = false)
    @Column(name = "MAX_AMOUNT_OF_UNITS_FOR_OBLIGATION", nullable = false, updatable = true)
    private Integer maxAmountOfUnitsForObligation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registeredServiceObligationStrategy")
    private List<Bond> bonds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public UserAccountInObligationGroup getUserAccountInObligationGroup() {
        return userAccountInObligationGroup;
    }

    public UserRegisteredService getUserRegisteredService() {
        return userRegisteredService;
    }

    public List<Bond> getBonds() {return bonds;}

    public UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public BigDecimal getAlreadyCreatedAmountOfMoney() {
        return alreadyCreatedAmountOfMoney.get();
    }

    public BigDecimal increaseCreatedMoney(final BigDecimal moneyToCreate){
        return this.alreadyCreatedAmountOfMoney.updateAndGet(amountOfCreatedMoney -> amountOfCreatedMoney.add(moneyToCreate));
    }

    public Integer getAlreadyObligatedUnitsOfWork() {
        return alreadyObligatedUnitsOfWork;
    }

    public Integer getAmountOfUnitsEverPaid() {
        return amountOfUnitsEverPaid;
    }

    public Integer getMaxAmountOfUnitsForObligation() {
        return maxAmountOfUnitsForObligation;
    }

    public int getMinAmountOfUnitsPerBond(){
        return this.minAmountOfUnitsPerBond;
    }

}
