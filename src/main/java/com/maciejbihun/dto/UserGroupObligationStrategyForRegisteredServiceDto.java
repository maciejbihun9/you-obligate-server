package com.maciejbihun.dto;

import com.maciejbihun.datatype.UnitOfWork;

import java.math.BigDecimal;

public class UserGroupObligationStrategyForRegisteredServiceDto {

    private Long id;

    private UserRegisteredServiceDto userRegisteredServiceDto;

    private ObligationGroupDto obligationGroupDto;

    private ObligationGroupAccountDto obligationGroupAccountDto;

    private UnitOfWork unitOfWork;

    private BigDecimal unitOfWorkCost;

    private BigDecimal interestRate;

    private BigDecimal alreadyCreatedAmountOfMoney;

    private int alreadyObligatedUnitsOfWork;

    private int amountOfUnitsEverPaid;

    private BigDecimal tooBigDebtFine;

    private int debtUnitsLimit;

    private int maxAmountOfUnitsForObligation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getAlreadyCreatedAmountOfMoney() {
        return alreadyCreatedAmountOfMoney;
    }

    public void setAlreadyCreatedAmountOfMoney(BigDecimal alreadyCreatedAmountOfMoney) {
        this.alreadyCreatedAmountOfMoney = alreadyCreatedAmountOfMoney;
    }

    public int getAlreadyObligatedUnitsOfWork() {
        return alreadyObligatedUnitsOfWork;
    }

    public void setAlreadyObligatedUnitsOfWork(int alreadyObligatedUnitsOfWork) {
        this.alreadyObligatedUnitsOfWork = alreadyObligatedUnitsOfWork;
    }

    public int getAmountOfUnitsEverPaid() {
        return amountOfUnitsEverPaid;
    }

    public void setAmountOfUnitsEverPaid(int amountOfUnitsEverPaid) {
        this.amountOfUnitsEverPaid = amountOfUnitsEverPaid;
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

    public UserRegisteredServiceDto getUserRegisteredServiceDto() {
        return userRegisteredServiceDto;
    }

    public void setUserRegisteredServiceDto(UserRegisteredServiceDto userRegisteredServiceDto) {
        this.userRegisteredServiceDto = userRegisteredServiceDto;
    }

    public ObligationGroupDto getObligationGroupDto() {
        return obligationGroupDto;
    }

    public void setObligationGroupDto(ObligationGroupDto obligationGroupDto) {
        this.obligationGroupDto = obligationGroupDto;
    }

    public ObligationGroupAccountDto getObligationGroupAccountDto() {
        return obligationGroupAccountDto;
    }

    public void setObligationGroupAccountDto(ObligationGroupAccountDto obligationGroupAccountDto) {
        this.obligationGroupAccountDto = obligationGroupAccountDto;
    }
}
