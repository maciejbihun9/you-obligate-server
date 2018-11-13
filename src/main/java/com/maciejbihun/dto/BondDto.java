package com.maciejbihun.dto;

public class BondDto {

    private Long id;

    private Integer amountOfUnitsToPay;

    private Long obligationStrategyId;

    private Long groupAccountId;

    public BondDto(Integer amountOfUnitsToPay, Long obligationStrategyId, Long groupAccountId) {
        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.obligationStrategyId = obligationStrategyId;
        this.groupAccountId = groupAccountId;
    }

    public Long getObligationStrategyId() {
        return obligationStrategyId;
    }

    public Long getGroupAccountId() {
        return groupAccountId;
    }

    public Integer getAmountOfUnitsToPay() {
        return amountOfUnitsToPay;
    }

    public void setAmountOfUnitsToPay(Integer amountOfUnitsToPay) {
        this.amountOfUnitsToPay = amountOfUnitsToPay;
    }
}
