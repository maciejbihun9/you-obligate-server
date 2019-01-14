package com.maciejbihun.service;

import com.maciejbihun.exceptions.ThereIsNoEnoughMoneyInAnAccountException;
import com.maciejbihun.exceptions.ThereIsNoEnoughUnitsToServeInBondException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.PurchaseCoupon;
import com.maciejbihun.models.User;


/**
 * @author Maciej Bihun
 */
public interface MarketTransactionsService {

    void buyPurchaseCoupon(User serviceCustomer, Bond bond, int amountOfServiceUnits);

    PurchaseCoupon makeCouponPurchase(User currentUser, Bond bond, int amountOfServiceUnits)
            throws ThereIsNoEnoughMoneyInAnAccountException, ThereIsNoEnoughUnitsToServeInBondException;

}
