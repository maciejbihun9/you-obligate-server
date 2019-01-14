package com.maciejbihun.dto;

import com.maciejbihun.models.Bond;

public class CouponPurchaseDataObjectDto {

    private BondDto bondDto;

    private Integer amountOfUnitsToBuy;

    public BondDto getBond() {
        return bondDto;
    }

    public void setBond(BondDto bondDto) {
        this.bondDto = bondDto;
    }

    public Integer getAmountOfUnitsToBuy() {
        return amountOfUnitsToBuy;
    }

    public void setAmountOfUnitsToBuy(Integer amountOfUnitsToBuy) {
        this.amountOfUnitsToBuy = amountOfUnitsToBuy;
    }
}
