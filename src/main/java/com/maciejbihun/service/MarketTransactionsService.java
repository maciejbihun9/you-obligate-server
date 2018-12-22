package com.maciejbihun.service;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.User;


/**
 * @author Maciej Bihun
 */
public interface MarketTransactionsService {

    void reserveServiceUnitForUser(User serviceCustomer, Bond issuedBond);

}
