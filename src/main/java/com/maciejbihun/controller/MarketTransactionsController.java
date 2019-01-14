package com.maciejbihun.controller;

import com.maciejbihun.dto.CouponPurchaseDataObjectDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Maciej Bihun
 */
public interface MarketTransactionsController {

    ResponseEntity<Object> makeCouponsPurchase(CouponPurchaseDataObjectDto couponPurchaseDataObjectDto);

}
