package com.maciejbihun.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Maciej Bihun
 * <p>
 * Represents an obligation between a user and a group.
 */
@Entity
@Table(name = "Bond")
public class Bond {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BOND_SEQ")
    @SequenceGenerator(name = "BOND_SEQ", sequenceName = "BOND_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
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

    @Basic(optional = false)
    @Column(name = "REPAYMENT_OBLIGATION_DATE_TIME", nullable = false, updatable = true)
    private LocalDateTime repaymentObligationDateTime;

    public Long getId() {
        return id;
    }

    public Integer getAmountOfUnitsToPay() {
        return amountOfUnitsToPay;
    }

    public void setAmountOfUnitsToPay(Integer amountOfUnitsToPay) {
        this.amountOfUnitsToPay = amountOfUnitsToPay;
    }

    public BigDecimal getUnitOfWorkCost() {
        return unitOfWorkCost;
    }

    public void setUnitOfWorkCost(BigDecimal unitOfWorkCost) {
        this.unitOfWorkCost = unitOfWorkCost;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getAmountOfCreatedMoney() {
        return amountOfCreatedMoney;
    }

    public void setAmountOfCreatedMoney(BigDecimal amountOfCreatedMoney) {
        this.amountOfCreatedMoney = amountOfCreatedMoney;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getRepaymentObligationDateTime() {
        return repaymentObligationDateTime;
    }

    public void setRepaymentObligationDateTime(LocalDateTime repaymentObligationDateTime) {
        this.repaymentObligationDateTime = repaymentObligationDateTime;
    }
}
