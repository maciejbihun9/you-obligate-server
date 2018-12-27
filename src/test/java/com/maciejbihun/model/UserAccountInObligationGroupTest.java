package com.maciejbihun.model;

import com.maciejbihun.exceptions.NotEnoughMoneyException;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountInObligationGroupTest {

    @Test
    public void afterSubtractionAccountBalanceShouldBeZeroWithTwoZerosAfterDot(){
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup();
        userAccountInObligationGroup.addMoney(BigDecimal.valueOf(100000, 2));
        userAccountInObligationGroup.subtractMoney(BigDecimal.valueOf(900000, 3));
        assertEquals(BigDecimal.valueOf(10000, 2), userAccountInObligationGroup.getAccountBalance());
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void shouldThrowNotEnoughMoneyException(){
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup();
        userAccountInObligationGroup.addMoney(BigDecimal.valueOf(100000, 2));
        userAccountInObligationGroup.subtractMoney(BigDecimal.valueOf(900000, 2));
    }

}
