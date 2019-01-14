package com.maciejbihun.dto;

public class BondDto {

    private Long id;

    private Integer amountOfUnitsToPay;

    private Long obligationStrategyId;

    public BondDto(Integer amountOfUnitsToPay, Long obligationStrategyId) {
        this.amountOfUnitsToPay = amountOfUnitsToPay;
        this.obligationStrategyId = obligationStrategyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObligationStrategyId() {
        return obligationStrategyId;
    }

    public Integer getAmountOfUnitsToPay() {
        return amountOfUnitsToPay;
    }

    public void setAmountOfUnitsToPay(Integer amountOfUnitsToPay) {
        this.amountOfUnitsToPay = amountOfUnitsToPay;
    }
}
