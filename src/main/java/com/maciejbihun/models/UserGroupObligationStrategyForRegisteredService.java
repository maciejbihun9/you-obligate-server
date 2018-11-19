package com.maciejbihun.models;

import com.maciejbihun.datatype.UnitOfWork;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 * Represents obligation strategy between the user registered service and concrete obligation group.
 */
@Entity
@Table(name="UserGroupObligationStrategyForRegisteredService")
public class UserGroupObligationStrategyForRegisteredService {

    private static final String NOT_ACCEPTED_AMOUNT_OF_UNITS_PER_BOND = "Minimal amount of units per bond should be grater than 1";

    private static final String NOT_ACCEPTED_INTEREST_RATE_VALUE = "Interest rate should be grater than 0.00";

    public UserGroupObligationStrategyForRegisteredService(){}

    public UserGroupObligationStrategyForRegisteredService(UserRegisteredService userRegisteredService, ObligationGroup obligationGroup,
                                                           UnitOfWork unitOfWork, BigDecimal unitOfWorkCost, BigDecimal interestRate) {
        this.userRegisteredService = userRegisteredService;
        this.obligationGroup = obligationGroup;
        this.unitOfWork = unitOfWork;
        this.unitOfWorkCost = unitOfWorkCost;
        this.interestRate = interestRate;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ")
    @SequenceGenerator(name = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ", sequenceName = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_REGISTERED_SERVICE_ID", nullable = false)
    private UserRegisteredService userRegisteredService;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

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
    @Basic(optional = false)
    @Column(name = "ALREADY_CREATED_AMOUNT_OF_MONEY", nullable = false, updatable = true)
    private BigDecimal alreadyCreatedAmountOfMoney = new BigDecimal("0.00");

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
    private Integer minAmountOfUnitsPerBond = 1;

    /**
     * The amount of units that the user is able to create for given service.
     */
    @Basic(optional = false)
    @Column(name = "DEBT_UNITS_LIMIT", nullable = false, updatable = true)
    private Integer debtUnitsLimit;

    /**
     * How many units a user might obligate at once.
     */
    @Basic(optional = false)
    @Column(name = "MAX_AMOUNT_OF_UNITS_FOR_OBLIGATION", nullable = false, updatable = true)
    private Integer maxAmountOfUnitsForObligation;

    public Long getId() {
        return id;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        int compareResult = new BigDecimal("0.00").compareTo(interestRate);
        if (compareResult >= 1){
            // picked interestRate was lower than 0.00 which is not acceptable
            throw new IllegalArgumentException(NOT_ACCEPTED_INTEREST_RATE_VALUE);
        }
        this.interestRate = interestRate;
    }

    public UserRegisteredService getUserRegisteredService() {
        return userRegisteredService;
    }

    public void setUserRegisteredService(UserRegisteredService userRegisteredService) {
        this.userRegisteredService = userRegisteredService;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public void setUnitOfWorkCost(BigDecimal unitOfWorkCost) {
        this.unitOfWorkCost = unitOfWorkCost;
    }

    public BigDecimal getAlreadyCreatedAmountOfMoney() {
        return alreadyCreatedAmountOfMoney;
    }

    public void setAlreadyCreatedAmountOfMoney(BigDecimal alreadyCreatedAmountOfMoney) {
        this.alreadyCreatedAmountOfMoney = alreadyCreatedAmountOfMoney;
    }

    public Integer getAlreadyObligatedUnitsOfWork() {
        return alreadyObligatedUnitsOfWork;
    }

    public void setAlreadyObligatedUnitsOfWork(Integer alreadyObligatedUnitsOfWork) {
        this.alreadyObligatedUnitsOfWork = alreadyObligatedUnitsOfWork;
    }

    public Integer getAmountOfUnitsEverPaid() {
        return amountOfUnitsEverPaid;
    }

    public void setAmountOfUnitsEverPaid(Integer amountOfUnitsEverPaid) {
        this.amountOfUnitsEverPaid = amountOfUnitsEverPaid;
    }

    public void setMinAmountOfUnitsPerBond(Integer minAmountOfUnitsPerBond) {
        this.minAmountOfUnitsPerBond = minAmountOfUnitsPerBond;
    }

    public Integer getDebtUnitsLimit() {
        return debtUnitsLimit;
    }

    public void setDebtUnitsLimit(Integer debtUnitsLimit) {
        this.debtUnitsLimit = debtUnitsLimit;
    }

    public Integer getMaxAmountOfUnitsForObligation() {
        return maxAmountOfUnitsForObligation;
    }

    public void setMaxAmountOfUnitsForObligation(Integer maxAmountOfUnitsForObligation) {
        this.maxAmountOfUnitsForObligation = maxAmountOfUnitsForObligation;
    }

    public void setMinAmountOfUnitsPerBond(int minAmountOfUnitsPerBond) throws IllegalArgumentException{
        if (minAmountOfUnitsPerBond < 1){
            throw new IllegalArgumentException(NOT_ACCEPTED_AMOUNT_OF_UNITS_PER_BOND);
        } else {
            this.minAmountOfUnitsPerBond = minAmountOfUnitsPerBond;
        }
    }

    public int getMinAmountOfUnitsPerBond(){
        return this.minAmountOfUnitsPerBond;
    }

}
