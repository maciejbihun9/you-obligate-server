package com.maciejbihun.service;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.User;


/**
 * @author Maciej Bihun
 */
public interface MarketTransactionsService {

    void buyPurchaseCoupon(User serviceCustomer, Bond bond, int amountOfServiceUnits);

    void makeCouponsPurchase();

}
