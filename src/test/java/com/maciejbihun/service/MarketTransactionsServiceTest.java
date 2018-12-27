package com.maciejbihun.service;

import com.maciejbihun.service.impl.MarketTransactionsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.maciejbihun.models.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MarketTransactionsServiceTest {

    private MarketTransactionsService marketTransactionsService;

    @Before
    public void init(){
        marketTransactionsService = new MarketTransactionsServiceImpl();
    }

    @Test
    public void buyPurchaseCoupon(){
        // given
        int amountOfServiceUnits = 10;
        User mockedCustomerUser = mock(User.class);
        Bond mockedBond = mock(Bond.class);
        ObligationGroup obligationGroup = mock(ObligationGroup.class);
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup();
        RegisteredServiceObligationStrategy mockedRegisteredServiceObligationStrategy = mock(RegisteredServiceObligationStrategy.class);

        Mockito.when(obligationGroup.getId()).thenReturn(1L);
        userAccountInObligationGroup.setObligationGroup(obligationGroup);
        userAccountInObligationGroup.addMoney(BigDecimal.valueOf(100100, 2));
        Mockito.when(mockedRegisteredServiceObligationStrategy.getUserAccountInObligationGroup()).thenReturn(userAccountInObligationGroup);
        Mockito.when(mockedBond.getUnitOfWorkCost()).thenReturn(BigDecimal.valueOf(10000, 2));
        Mockito.when(mockedBond.getRegisteredServiceObligationStrategy()).thenReturn(mockedRegisteredServiceObligationStrategy);

        // Mockito.when(mockedBond.).thenReturn(1L);
        Mockito.when(mockedCustomerUser.getUserAccountInObligationGroups()).thenReturn(Arrays.asList(userAccountInObligationGroup));

        // when
        marketTransactionsService.buyPurchaseCoupon(mockedCustomerUser, mockedBond, amountOfServiceUnits);

        // then
        assertEquals(BigDecimal.valueOf(1, 2), userAccountInObligationGroup.getAccountBalance());
    }
}
