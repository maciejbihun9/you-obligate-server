package com.maciejbihun.models;

import com.maciejbihun.converters.AtomicReferenceConverter;
import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.NotEnoughUnitsException;

import javax.persistence.*;
import java.math.BigDecimal;
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
 *
 * TESTED
 *
 */
@Entity
@Table(name = "Bond")
public class Bond {

    private static final Logger BOND_LOGGER = Logger.getLogger(Bond.class.getName());

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BOND_SEQ")
    @SequenceGenerator(name = "BOND_SEQ", sequenceName = "BOND_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "OBLIGATION_GROUP_ID", updatable = false, nullable = false)
    private Integer obligationGroupId;

    @Basic(optional = false)
    @Column(name = "BOND_STATUS", nullable = false)
    private BondStatus bondStatus = BondStatus.CREATED;

    @Basic(optional = false)
    @Column(name = "NUMBER_OF_UNITS_TO_SERVE", updatable = true)
    private Integer numberOfUnitsToServe;

    @Basic(optional = false)
    @Column(name = "UNIT_OF_WORK_COST", updatable = false)
    private BigDecimal unitOfWorkCost;

    @Convert(converter = AtomicReferenceConverter.class)
    @Column(name = "AMOUNT_OF_CREATED_MONEY", length = 400, updatable = true)
    private BigDecimal amountOfCreatedMoney = BigDecimal.ZERO;

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
    private List<PurchaseCoupon> purchaseCoupons = new ArrayList<>();

    public synchronized Integer subtractUnits(int unitsToSubtract){
        if (unitsToSubtract > numberOfUnitsToServe){
            throw new NotEnoughUnitsException();
        }
        numberOfUnitsToServe = numberOfUnitsToServe - unitsToSubtract;
        if (numberOfUnitsToServe == 0){
            bondStatus = BondStatus.LACK_OF_UNITS;
            BOND_LOGGER.log(Level.INFO, String.format("Service units for bond with id: %s has been used.", id));
        }
        return numberOfUnitsToServe;
    }

    public Long getId() {
        return id;
    }

    public Integer getObligationGroupId() {
        return obligationGroupId;
    }

    public void setObligationGroupId(Integer obligationGroupId) {
        this.obligationGroupId = obligationGroupId;
    }

    public void setBondStatus(BondStatus bondStatus) {
        this.bondStatus = bondStatus;
    }

    public BondStatus getBondStatus() {
        return bondStatus;
    }

    public void setNumberOfUnitsToServe(Integer numberOfUnitsToServe) {
        this.numberOfUnitsToServe = numberOfUnitsToServe;
    }

    public Integer getNumberOfUnitsToServe() {
        return this.numberOfUnitsToServe;
    }

    public void setUnitOfWorkCost(BigDecimal unitOfWorkCost) {
        this.unitOfWorkCost = unitOfWorkCost;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public void setAmountOfCreatedMoney(BigDecimal amountOfCreatedMoney) {
        this.amountOfCreatedMoney = amountOfCreatedMoney;
    }

    public BigDecimal getAmountOfCreatedMoney() {
        return amountOfCreatedMoney;
    }

    public void setRegisteredServiceObligationStrategy(RegisteredServiceObligationStrategy registeredServiceObligationStrategy) {
        this.registeredServiceObligationStrategy = registeredServiceObligationStrategy;
    }

    public RegisteredServiceObligationStrategy getRegisteredServiceObligationStrategy() {
        return registeredServiceObligationStrategy;
    }

    public List<PurchaseCoupon> getPurchaseCoupons() {
        return purchaseCoupons;
    }

    public void setObligationClosedDateTime(LocalDateTime obligationClosedDateTime) {
        this.obligationClosedDateTime = obligationClosedDateTime;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getObligationClosedDateTime() {
        return this.obligationClosedDateTime;
    }

}
