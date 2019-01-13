package com.maciejbihun.controller;

import com.maciejbihun.dto.CouponPurchaseDataObjectDto;

/**
 * @author Maciej Bihun
 */
public interface MarketTransactionsController {

    void makeCouponsPurchase(CouponPurchaseDataObjectDto couponPurchaseDataObjectDto);

}
