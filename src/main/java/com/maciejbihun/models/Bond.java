package com.maciejbihun.models;

import com.maciejbihun.datatype.BondStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maciej Bihun
 * <p>
 * Represents an obligation between a user and a group.
 */
@Entity
@Table(name = "Bond")
public class Bond {

    private static final Logger BOND_LOGGER = Logger.getLogger(Bond.class.getName());

    private static final String OBLIGATION_CLOSED_MESSAGE = "Obligation is closed, because has been paid";

    private static final String NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND = "Not acceptable amount of units per bond.";

    private Bond(){}

    public Bond(ObligationGroupAccount obligationGroupAccount,
                UserGroupObligationStrategyForRegisteredService obligationStrategy, Integer amountOfUnitsToPay) throws IllegalArgumentException {

        if (amountOfUnitsToPay < obligationStrategy.getMinAmountOfUnitsPerBond()){
            throw new IllegalArgumentException(String.format(NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND + " It was %s, but it should be %s",
                    amountOfUnitsToPay, obligationStrategy.getMinAmountOfUnitsPerBond()));
        }
        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.obligationGroupAccount = obligationGroupAccount;
        this.interestRate = obligationStrategy.getInterestRate();
        this.obligationGroup = obligationStrategy.getObligationGroup();
        this.unitOfWorkCost = obligationStrategy.getUnitOfWorkCost().subtract(this.interestRate.multiply(obligationStrategy.getUnitOfWorkCost()));
        this.amountOfCreatedMoney = this.unitOfWorkCost.multiply(new BigDecimal(this.amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP);
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BOND_SEQ")
    @SequenceGenerator(name = "BOND_SEQ", sequenceName = "BOND_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "BOND_STATUS", updatable = true)
    private BondStatus bondStatus = BondStatus.CREATED;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ACCOUNT_ID", nullable = false)
    private ObligationGroupAccount obligationGroupAccount;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_UNITS_TO_PAY", updatable = true)
    private Integer amountOfUnitsToPay;

    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK_COST", updatable = false)
    private BigDecimal unitOfWorkCost;

    @Basic(optional = false)
    @Column(name = "INTEREST_RATE", updatable = false)
    private BigDecimal interestRate;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", updatable = true)
    private BigDecimal amountOfCreatedMoney;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", nullable = false)
    private ObligationGroup obligationGroup;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = true)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Basic(optional = true)
    @Column(name = "OBLIGATION_CLOSED_DATE_TIME", nullable = true, updatable = true)
    private LocalDateTime obligationClosedDateTime;

    public void substractBondUnit(){
        if (bondStatus.equals(BondStatus.CLOSED)){
            BOND_LOGGER.log(Level.INFO, OBLIGATION_CLOSED_MESSAGE);
        }
        if (this.amountOfUnitsToPay > 1){
            this.amountOfUnitsToPay = this.amountOfUnitsToPay - 1;
            this.amountOfCreatedMoney = this.amountOfCreatedMoney.subtract(this.unitOfWorkCost);
        } else if(this.amountOfUnitsToPay == 1) {
            this.amountOfUnitsToPay = this.amountOfUnitsToPay - 1;
            this.amountOfCreatedMoney = this.amountOfCreatedMoney.subtract(this.unitOfWorkCost);
            this.obligationClosedDateTime = LocalDateTime.now();
            this.bondStatus = BondStatus.CLOSED;
            BOND_LOGGER.log(Level.INFO, String.format("Obligation: %s has been closed", id));
        }
    }

    public Long getId() {
        return id;
    }

    public ObligationGroupAccount getObligationGroupAccount() {return obligationGroupAccount;}

    public Integer getAmountOfUnitsToPay() {
        return amountOfUnitsToPay;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getAmountOfCreatedMoney() {
        return amountOfCreatedMoney;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getObligationClosedDateTime() {
        return this.obligationClosedDateTime;
    }

    private void closeObligation() {
        this.obligationClosedDateTime = LocalDateTime.now();
        this.bondStatus = BondStatus.CLOSED;
        BOND_LOGGER.log(Level.INFO , String.format("Closing obligation"));
    }
}
