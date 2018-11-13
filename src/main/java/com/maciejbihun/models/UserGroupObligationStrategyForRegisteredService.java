package com.maciejbihun.models;

import com.maciejbihun.datatype.UnitOfWork;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name="UserGroupObligationStrategyForRegisteredService")
public class UserGroupObligationStrategyForRegisteredService {

    private static final Logger LOGGER = Logger.getLogger(UserGroupObligationStrategyForRegisteredService.class.getName());

    private static final String NOT_ACCEPTED_AMOUNT_OF_UNITS_PER_BOND = "Minimal amount of units per bond should be grater than 1";

    private static final String NOT_ACCEPTED_INTEREST_RATE_VALUE = "Interest rate should be grater than 0.00";

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

    /**
     * Each bond has to have minimal amount of units. The default value is 1.
     */
    private int minAmountOfUnitsPerBond = 1;

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
        int compareResult = new BigDecimal("0.00").compareTo(interestRate);
        if (compareResult >= 1){
            // picked interestRate was lower than 0.00 which is not acceptable
            throw new IllegalArgumentException(NOT_ACCEPTED_INTEREST_RATE_VALUE);
        }
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
