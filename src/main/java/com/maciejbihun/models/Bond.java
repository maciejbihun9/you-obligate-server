package com.maciejbihun.models;

import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.EmptyConstructorIsNotAvailableException;
import com.maciejbihun.exceptions.NegativeValueException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * <p>
 * Represents an obligation between a user, his registered service and a group.
 * A user obligates to do given amount of units of work for group members.
 * A user receives given amount of money for his obligation.
 * @author Maciej Bihun
 */
@Entity
@Table(name = "Bond")
public class Bond {

    private static final Logger BOND_LOGGER = Logger.getLogger(Bond.class.getName());

    private static final String OBLIGATION_CLOSED_MESSAGE = "Obligation is closed, because has been paid. You can not subtract units from obligation that has been paid";

    private static final String NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND = "Not acceptable amount of units per bond.";

    public Bond(){
        throw new EmptyConstructorIsNotAvailableException();
    }

    public Bond(UserGroupObligationStrategyForRegisteredService obligationStrategy, Integer amountOfUnitsToPay) {
        if (amountOfUnitsToPay < obligationStrategy.getMinAmountOfUnitsPerBond()){
            throw new IllegalArgumentException(String.format(NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND + " It was %s, but it should be at least %s.",
                    amountOfUnitsToPay, obligationStrategy.getMinAmountOfUnitsPerBond()));
        }
        if (obligationStrategy.getUnitOfWorkCost().compareTo(BigDecimal.ZERO) < 0 || obligationStrategy.getInterestRate().compareTo(BigDecimal.ZERO) < 0 || amountOfUnitsToPay < 0){
            throw new NegativeValueException();
        }
        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.unitOfWorkCost = obligationStrategy.getUnitOfWorkCost().subtract(obligationStrategy.getInterestRate().multiply(obligationStrategy.getUnitOfWorkCost()));
        this.amountOfCreatedMoney = unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP);
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BOND_SEQ")
    @SequenceGenerator(name = "BOND_SEQ", sequenceName = "BOND_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "BOND_STATUS", updatable = true)
    private BondStatus bondStatus = BondStatus.CREATED;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_UNITS_TO_PAY", updatable = true)
    private Integer amountOfUnitsToPay;

    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK_COST", updatable = false)
    private BigDecimal unitOfWorkCost;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", updatable = true)
    private BigDecimal amountOfCreatedMoney;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = true)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Basic(optional = true)
    @Column(name = "OBLIGATION_CLOSED_DATE_TIME", nullable = true, updatable = true)
    private LocalDateTime obligationClosedDateTime;

    public String subtractBondUnit() throws Exception {
        if (bondStatus.equals(BondStatus.CLOSED)){
            BOND_LOGGER.log(Level.INFO, OBLIGATION_CLOSED_MESSAGE);
            throw new Exception(OBLIGATION_CLOSED_MESSAGE);
        }
        if (this.amountOfUnitsToPay > 1){
            this.amountOfUnitsToPay = this.amountOfUnitsToPay - 1;
            this.amountOfCreatedMoney = this.amountOfCreatedMoney.subtract(this.unitOfWorkCost);
            return "Operation went well. One unit subtracted from bond.";
        } else if(this.amountOfUnitsToPay == 1) {
            this.amountOfUnitsToPay = this.amountOfUnitsToPay - 1;
            this.amountOfCreatedMoney = this.amountOfCreatedMoney.subtract(this.unitOfWorkCost);
            this.obligationClosedDateTime = LocalDateTime.now();
            this.bondStatus = BondStatus.CLOSED;
            BOND_LOGGER.log(Level.INFO, String.format("Obligation: %s has been closed", id));
            return "Operation went well and bond has been paid. Congratulations!!!";
        }
        return "";
    }

    public Long getId() {
        return id;
    }

    public BondStatus getBondStatus() {
        return bondStatus;
    }

    public void setBondStatus(BondStatus bondStatus) {
        this.bondStatus = bondStatus;
    }

    public Integer getAmountOfUnitsToPay() {
        return amountOfUnitsToPay;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public BigDecimal getAmountOfCreatedMoney() {
        return amountOfCreatedMoney;
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
