package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.NotEnoughMoneyException;
import com.maciejbihun.models.*;
import com.maciejbihun.service.MarketTransactionsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Maciej Bihun
 */
@Service
public class MarketTransactionsServiceImpl implements MarketTransactionsService {

    @Override
    public void buyPurchaseCoupon(User serviceCustomer, Bond issuedBond, int amountOfServiceUnits) {
        PurchaseCoupon purchaseCoupon = new PurchaseCoupon();
        purchaseCoupon.setOwner(serviceCustomer);
        purchaseCoupon.setBond(issuedBond);
        purchaseCoupon.setServiceUnits(amountOfServiceUnits);
        serviceCustomer.getPurchaseCoupons().add(purchaseCoupon);

        serviceCustomer.getUserAccountInObligationGroups().forEach(userAccountInObligationGroup -> {
            if (userAccountInObligationGroup.getObligationGroup().equals(issuedBond.getRegisteredServiceObligationStrategy()
                    .getUserAccountInObligationGroup().getObligationGroup())){

                // check if s user has enough money to make a purchase
                BigDecimal purchaseTotalPrice = issuedBond.getUnitOfWorkCost().multiply(BigDecimal.valueOf(amountOfServiceUnits));
                if (userAccountInObligationGroup.getAccountBalance().compareTo(purchaseTotalPrice) < 0){
                    throw new NotEnoughMoneyException();
                }

                // subtract money from user account
                userAccountInObligationGroup.subtractMoney(purchaseTotalPrice.setScale(2, RoundingMode.UP));

                // subtract amountOfServiceUnits units from bond
                issuedBond.subtractUnits(amountOfServiceUnits);
            }
        });
    }

}
