package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.ClosedBondException;
import com.maciejbihun.exceptions.EmptyConstructorIsNotAvailableException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * <p>
 * Represents an obligation between a User, his registered service in a group.
 * A user obligates to do given amount of units of work for group members.
 * A user receives given amount of money for his obligation.
 * After closing, bond should disappear from user interface.
 * Operations on closed bond will throw
 * @author Maciej Bihun
 */
@Entity
@Table(name = "Bond")
public class Bond {

    private static final Logger BOND_LOGGER = Logger.getLogger(Bond.class.getName());

    private static final String NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND = "Not acceptable amount of units per bond.";

    public Bond(){
        throw new EmptyConstructorIsNotAvailableException();
    }

    public Bond(RegisteredServiceObligationStrategy obligationStrategy, Integer amountOfUnitsToPay) {
        if (amountOfUnitsToPay < obligationStrategy.getMinAmountOfUnitsPerBond()){
            throw new IllegalArgumentException(String.format(NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND + " It was %s, but it should be at least %s.",
                    amountOfUnitsToPay, obligationStrategy.getMinAmountOfUnitsPerBond()));
        }
        this.obligationStrategy = obligationStrategy;
        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.unitOfWorkCost = obligationStrategy.getUnitOfWorkCost().subtract(obligationStrategy.getInterestRate().multiply(obligationStrategy.getUnitOfWorkCost()));
        this.amountOfCreatedMoney.updateAndGet(bigDecimal -> unitOfWorkCost.multiply(new BigDecimal(amountOfUnitsToPay)).setScale(2, RoundingMode.HALF_UP));
    }

    @Id
    @Column(name = "ID", updatable = false)
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

    @Convert(converter = AtomicReferenceConverter.class)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", length = 400, updatable = true)
    private final AtomicReference<BigDecimal> amountOfCreatedMoney = new AtomicReference<>(BigDecimal.ZERO);

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Basic(optional = true)
    @Column(name = "OBLIGATION_CLOSED_DATE_TIME", nullable = true, updatable = true)
    private LocalDateTime obligationClosedDateTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_STRATEGY_ID", nullable = false)
    private RegisteredServiceObligationStrategy obligationStrategy;

    public Integer subtractBondUnit() throws ClosedBondException {
        if (bondStatus.equals(BondStatus.CLOSED)){
            throw new ClosedBondException();
        }
        if (this.amountOfUnitsToPay == 1){
            this.amountOfUnitsToPay = 0;
            this.amountOfCreatedMoney.updateAndGet(createdMoney -> BigDecimal.valueOf(0, 2));
            this.obligationClosedDateTime = LocalDateTime.now();
            this.bondStatus = BondStatus.CLOSED;
            BOND_LOGGER.log(Level.INFO, String.format("Obligation: %s has been closed", id));
            return this.amountOfUnitsToPay;
        } else {
            this.amountOfCreatedMoney.updateAndGet(createdMoney -> createdMoney.subtract(this.unitOfWorkCost));
            this.amountOfUnitsToPay = this.amountOfUnitsToPay - 1;
            return this.amountOfUnitsToPay;
        }
    }

    public RegisteredServiceObligationStrategy getObligationStrategy() {
        return obligationStrategy;
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
        return amountOfCreatedMoney.get();
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getObligationClosedDateTime() {
        return this.obligationClosedDateTime;
    }

}
