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

    private static final String OBLIGATION_CLOSED_MESSAGE = "Obligation is closed, because has been paid. You can not subtract units from obligation that has been paid";

    private Bond(){}

    public Bond(UserAccountInObligationGroup userAccountInObligationGroup,
                UserGroupObligationStrategyForRegisteredService obligationStrategy,
                Integer amountOfUnitsToPay,
                BigDecimal unitOfWorkCost,
                BigDecimal amountOfCreatedMoney)
    {

        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.userAccountInObligationGroup = userAccountInObligationGroup;
        this.interestRate = obligationStrategy.getInterestRate();
        this.obligationGroup = obligationStrategy.getObligationGroup();
        this.unitOfWorkCost = unitOfWorkCost;
        this.amountOfCreatedMoney = amountOfCreatedMoney;
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
    private UserAccountInObligationGroup userAccountInObligationGroup;

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

    public UserAccountInObligationGroup getUserAccountInObligationGroup() {return userAccountInObligationGroup;}

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
