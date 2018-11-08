package com.maciejbihun.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name="UserGroupObligationStrategyForRegisteredService")
public class UserGroupObligationStrategyForRegisteredService {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ")
    @SequenceGenerator(name = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ", sequenceName = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_REGISTERED_SERVICE_ID", nullable = false)
    private UserRegisteredService userRegisteredService;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_GROUP_OBLIGATION_STRATEGY_FOR_REGISTERED_SERVICE_ID", nullable = false)
    private ObligationGroup obligationGroup;

    private UnitOfWork unitOfWork;

    private BigDecimal unitOfWorkCost;

    /**
     * Represents the interest rate on the debt. It might be changed by group of users any time
     */
    private BigDecimal interestRate = new BigDecimal("0.00");

    /**
     * Represents already created amount of money in given group based on obligated amount of work
     */
    private BigDecimal alreadyCreatedAmountOfMoney = new BigDecimal("0.00");

    /**
     * Represents already obligated units of work that needs to be paid back
     */
    private int alreadyObligatedUnitsOfWork = 0;

    /**
     * Represents amount of units that user already has paid in given group
     */
    private int amountOfUnitsEverPaid = 0;

    /**
     * This value will be added to interest rate when a user crosses the border a limit
     */
    private BigDecimal tooBigDebtFine = new BigDecimal("0");

    private int debtUnitsLimit;

    /**
     * How many units a user might obligate at once
     */
    private int maxAmountOfUnitsForObligation;

    public Long getId() {
        return id;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
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

    public void appendCreatedMoney(BigDecimal moneyToCreate) {
        this.alreadyCreatedAmountOfMoney = moneyToCreate;
    }

    public int getAlreadyObligatedUnitsOfWork() {
        return alreadyObligatedUnitsOfWork;
    }

    public void obligateUnitsOfWork(int unitsOfWorkToObligate) {
        this.alreadyObligatedUnitsOfWork = unitsOfWorkToObligate;
    }

    public int getAmountOfUnitsEverPaid() {
        return amountOfUnitsEverPaid;
    }

    public void appendPaidUnits(int paidUnits) {
        this.amountOfUnitsEverPaid = paidUnits;
    }

    public BigDecimal getTooBigDebtFine() {
        return tooBigDebtFine;
    }

    public void setTooBigDebtFine(BigDecimal tooBigDebtFine) {
        this.tooBigDebtFine = tooBigDebtFine;
    }

    public int getDebtUnitsLimit() {
        return debtUnitsLimit;
    }

    public void setDebtUnitsLimit(int debtUnitsLimit) {
        this.debtUnitsLimit = debtUnitsLimit;
    }

    public int getMaxAmountOfUnitsForObligation() {
        return maxAmountOfUnitsForObligation;
    }

    public void setMaxAmountOfUnitsForObligation(int maxAmountOfUnitsForObligation) {
        this.maxAmountOfUnitsForObligation = maxAmountOfUnitsForObligation;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public UserRegisteredService getUserRegisteredService() {
        return userRegisteredService;
    }

    public void setUserRegisteredService(UserRegisteredService userRegisteredService) {
        this.userRegisteredService = userRegisteredService;
    }
}
