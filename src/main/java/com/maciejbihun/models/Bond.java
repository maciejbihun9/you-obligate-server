package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.ClosedBondException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public Bond(){}

    public Bond(RegisteredServiceObligationStrategy registeredServiceObligationStrategy, Integer amountOfServiceUnitsToBeDelivered) {
        if (amountOfServiceUnitsToBeDelivered < registeredServiceObligationStrategy.getMinAmountOfUnitsPerBond()){
            throw new IllegalArgumentException(String.format(NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND + " It was %s, but it should be at least %s.",
                    amountOfServiceUnitsToBeDelivered, registeredServiceObligationStrategy.getMinAmountOfUnitsPerBond()));
        }
        this.registeredServiceObligationStrategy = registeredServiceObligationStrategy;
        this.amountOfServiceUnitsToBeDelivered = amountOfServiceUnitsToBeDelivered;
        this.unitOfWorkCost = registeredServiceObligationStrategy.getUnitOfWorkCost()
                .subtract(registeredServiceObligationStrategy.getInterestRate().multiply(registeredServiceObligationStrategy.getUnitOfWorkCost()));
        this.availableBalance = unitOfWorkCost.multiply(new BigDecimal(amountOfServiceUnitsToBeDelivered)).setScale(2, RoundingMode.HALF_UP);
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
    @Column(name = "AMOUNT_OF_SERVICE_UNITS_TO_BE_DELIVERED", updatable = true)
    private Integer amountOfServiceUnitsToBeDelivered;

    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_RESERVED_SERVICE_UNITS", updatable = true)
    private Integer amountOfReservedServiceUnits = 0;

    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK_COST", updatable = false)
    private BigDecimal unitOfWorkCost;

    @Convert(converter = AtomicReferenceConverter.class)
    @Column(name = "AVAILABLE_BALANCE", length = 400, updatable = true)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    /**
     * When owner of this bond is making a reservation of a service unit
     * then money created from this bond is blocked. Given amount of money
     * is transferred from available balance to blocked balance.
     * When other users are making reservations of service units then
     * only reserved units number is increasing where available units is decreasing
     */
    @Convert(converter = AtomicReferenceConverter.class)
    @Column(name = "BLOCKED_BALANCE", length = 400, updatable = true)
    private BigDecimal blockedBalance = BigDecimal.ZERO;

    @Basic(optional = false)
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Basic(optional = true)
    @Column(name = "OBLIGATION_CLOSED_DATE_TIME", nullable = true, updatable = true)
    private LocalDateTime obligationClosedDateTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_STRATEGY_ID", nullable = false)
    private RegisteredServiceObligationStrategy registeredServiceObligationStrategy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bond")
    private List<PurchaseToken> purchaseTokens = new ArrayList<>();

    public List<PurchaseToken> getPurchaseTokens() {
        return purchaseTokens;
    }

    /**
     *
     */
    public synchronized Integer subtractBondUnit() throws ClosedBondException {
        if (bondStatus.equals(BondStatus.CLOSED)){
            throw new ClosedBondException();
        }
        if (this.amountOfServiceUnitsToBeDelivered == 1){
            this.amountOfServiceUnitsToBeDelivered = 0;
            this.bondStatus = BondStatus.PAID;
            BOND_LOGGER.log(Level.INFO, String.format("Obligation: %s has been paid", id));
        } else {
            this.amountOfServiceUnitsToBeDelivered = this.amountOfServiceUnitsToBeDelivered - 1;
        }
        return this.amountOfServiceUnitsToBeDelivered;
    }

    /**
     * Adds one unit to reserved units and adds corresponding amount of created money.
     * Returns changed amount of created reservedUnits.
     */
    public synchronized Integer addReservedBondUnit(){
        this.amountOfReservedServiceUnits = this.amountOfReservedServiceUnits + 1;
        return this.amountOfReservedServiceUnits;
    }

    public RegisteredServiceObligationStrategy getRegisteredServiceObligationStrategy() {
        return registeredServiceObligationStrategy;
    }

    public Long getId() {
        return id;
    }

    public Integer getReservedUnits() {
        return this.amountOfReservedServiceUnits;
    }

    public BondStatus getBondStatus() {
        return bondStatus;
    }

    public void setBondStatus(BondStatus bondStatus) {
        this.bondStatus = bondStatus;
    }

    public synchronized Integer getAmountOfServiceUnitsToBeDelivered() {
        return this.amountOfServiceUnitsToBeDelivered;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public synchronized BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getObligationClosedDateTime() {
        return this.obligationClosedDateTime;
    }

    public BigDecimal getBlockedBalance() {
        return blockedBalance;
    }

    /**
     * Reserves one unit
     * Returns current amount of reserved units
     */
    public synchronized void reserveUnit(){
        // subtract one unit from availableUnits
        subtractBondUnit();

        // add one unit to reserved units
        addReservedBondUnit();
    }

}
