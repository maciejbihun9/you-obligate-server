package com.maciejbihun.service;

import com.maciejbihun.service.impl.MarketTransactionsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.maciejbihun.models.*;

@RunWith(MockitoJUnitRunner.class)
public class MarketTransactionsServiceTest {

    private MarketTransactionsService marketTransactionsService;

    @Before
    public void init(){
        marketTransactionsService = new MarketTransactionsServiceImpl();
    }

    @Test
    public void reserveServiceUnit(){
        User mockedUser = Mockito.mock(User.class);
        Bond issuedBond = Mockito.mock(Bond.class);
        marketTransactionsService.reserveServiceUnitForUser(mockedUser, issuedBond);
    }

    @Test
    public void shouldDecreaseAmountOfMoneyInUserAccountInObligationGroup(){

    }

}
