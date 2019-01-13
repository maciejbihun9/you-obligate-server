package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.MarketTransactionsController;
import com.maciejbihun.dto.CouponPurchaseDataObjectDto;
import com.maciejbihun.service.MarketTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maciej Bihun
 */
@RestController(value = "/market-transactions")
public class MarketTransactionsControllerImpl implements MarketTransactionsController {

    @Autowired
    MarketTransactionsService marketTransactionsService;

    @Override
    @PostMapping("/make-coupons-purchase")
    public void makeCouponsPurchase(@RequestBody CouponPurchaseDataObjectDto couponPurchaseDataObjectDto) {
        marketTransactionsService.makeCouponsPurchase();
    }
}
