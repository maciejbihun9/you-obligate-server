package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.NotEnoughMoneyException;
import com.maciejbihun.exceptions.ThereIsNoEnoughMoneyInAnAccountException;
import com.maciejbihun.exceptions.ThereIsNoEnoughUnitsToServeInBondException;
import com.maciejbihun.models.*;
import com.maciejbihun.service.MarketTransactionsService;
import com.maciejbihun.service.UserAccountInObligationGroupService;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Maciej Bihun
 */
@Service
public class MarketTransactionsServiceImpl implements MarketTransactionsService {

    @Autowired
    UserAccountInObligationGroupService userAccountInObligationGroupService;

    @Autowired
    UserService userService;

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

    @Override
    public PurchaseCoupon makeCouponPurchase(User serviceCustomer, Bond issuedBond, int amountOfServiceUnits)
            throws ThereIsNoEnoughMoneyInAnAccountException, ThereIsNoEnoughUnitsToServeInBondException {



        // get user account for given user in obligation group
        UserAccountInObligationGroup userAccountInObligationGroupForObligationGroupAndUser =
                userAccountInObligationGroupService.getUserAccountInObligationGroupForObligationGroupAndUser(serviceCustomer.getId(), issuedBond.getId());

        BigDecimal userAccountBalanceInObligationGroup = userAccountInObligationGroupForObligationGroupAndUser.getAccountBalance();

        BigDecimal moneyToWithdraw = issuedBond.getUnitOfWorkCost().multiply(BigDecimal.valueOf(amountOfServiceUnits));
        // check if user has enough money
        if (moneyToWithdraw.compareTo(userAccountBalanceInObligationGroup) > 0){
            throw new ThereIsNoEnoughMoneyInAnAccountException();
        }
        if (amountOfServiceUnits > issuedBond.getNumberOfUnitsToServe()){
            throw new ThereIsNoEnoughUnitsToServeInBondException();
        }

        // subtract money from user account
        userAccountInObligationGroupForObligationGroupAndUser.subtractMoney(moneyToWithdraw);

        // subtract units from bond
        issuedBond.subtractUnits(amountOfServiceUnits);

        // create purchase coupon
        PurchaseCoupon purchaseCoupon = new PurchaseCoupon();
        purchaseCoupon.setOwner(serviceCustomer);
        purchaseCoupon.setBond(issuedBond);
        purchaseCoupon.setServiceUnits(amountOfServiceUnits);
        purchaseCoupon.setTotalCost(moneyToWithdraw);
        serviceCustomer.getPurchaseCoupons().add(purchaseCoupon);

        // save user account data and issued bond
        userAccountInObligationGroupService.saveUserAccountInObligationGroup(userAccountInObligationGroupForObligationGroupAndUser);

        // save user
        userService.saveUser(serviceCustomer);

        return purchaseCoupon;

    }

}
