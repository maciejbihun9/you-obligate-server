package com.maciejbihun.dto;

import com.maciejbihun.models.ObligationGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BondDto {

    private Long id;

    private Integer amountOfUnitsToPay;

    private BigDecimal unitOfWorkCost;

    private BigDecimal interestRate;

    private BigDecimal amountOfCreatedMoney;

    private ObligationGroupDto obligationGroupDto;

    private LocalDateTime createdDateTime;

    private LocalDateTime repaymentObligationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ObligationGroupDto getObligationGroupDto() {
        return obligationGroupDto;
    }

    public void setObligationGroupDto(ObligationGroupDto obligationGroupDto) {
        this.obligationGroupDto = obligationGroupDto;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getRepaymentObligationDateTime() {
        return repaymentObligationDateTime;
    }

    public void setRepaymentObligationDateTime(LocalDateTime repaymentObligationDateTime) {
        this.repaymentObligationDateTime = repaymentObligationDateTime;
    }
}
