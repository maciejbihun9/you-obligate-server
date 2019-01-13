package com.maciejbihun.dto;

import com.maciejbihun.models.Bond;

public class CouponPurchaseDataObjectDto {

    private Bond bond;

    private Integer amountOfUnitsToBuy;

    public Bond getBond() {
        return bond;
    }

    public void setBond(Bond bond) {
        this.bond = bond;
    }

    public Integer getAmountOfUnitsToBuy() {
        return amountOfUnitsToBuy;
    }

    public void setAmountOfUnitsToBuy(Integer amountOfUnitsToBuy) {
        this.amountOfUnitsToBuy = amountOfUnitsToBuy;
    }
}
