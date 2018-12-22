package com.maciejbihun.service.impl;

import com.maciejbihun.models.*;
import com.maciejbihun.service.MarketTransactionsService;
import org.springframework.stereotype.Service;

/**
 * @author Maciej Bihun
 */
@Service
public class MarketTransactionsServiceImpl implements MarketTransactionsService {

    /**
     * Reserves one service unit which was issued by a bond issuer.
     */
    @Override
    public void reserveServiceUnitForUser(User serviceCustomer, Bond issuedBond) {

        // make a reservation for one unit of work using this purchase token
        PurchaseToken purchaseToken = new PurchaseToken();
        purchaseToken.setBond(issuedBond);
        purchaseToken.setCustomer(serviceCustomer);

        serviceCustomer.getUserAccountInObligationGroups().forEach(userAccountInObligationGroup -> {
            if (userAccountInObligationGroup.getObligationGroup() ==
                    issuedBond.getRegisteredServiceObligationStrategy().getUserAccountInObligationGroup().getObligationGroup()){

                // block money for service Customer
                userAccountInObligationGroup.blockMoney(issuedBond.getUnitOfWorkCost());

                // reserve one unit from issued bond
                issuedBond.reserveUnit();
            }
        });
    }

}
