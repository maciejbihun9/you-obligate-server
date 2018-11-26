package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserAccountInObligationGroupControllerIntegrationTest {

    @Autowired
    UserAccountInObligationGroupController obligationGroupAccountService;

    @Autowired
    BondController bondController;

    @Test
    public void getUserAccountInObligationGroupWithBonds(){

        // given
        Long obligationStrategyId = 1L;
        Long obligationGroupAccountId = 1L;
        Integer amountOfUnitsToPay = 100;
        BondDto bondDto = new BondDto(amountOfUnitsToPay, obligationStrategyId, obligationGroupAccountId);

        // when
        bondController.createBondInObligationGroup(bondDto);

        // then
        ResponseEntity<UserAccountInObligationGroup> userAccountInObligationGroupWithBonds =
                obligationGroupAccountService.getUserAccountInObligationGroupWithBonds(obligationGroupAccountId);
        assertEquals(amountOfUnitsToPay ,userAccountInObligationGroupWithBonds.getBody().getBonds().get(0).getAmountOfUnitsToPay());
        assertEquals(HttpStatus.OK, userAccountInObligationGroupWithBonds.getStatusCode());

        Long notExistingObligationGroupAccountId = 10000L;
        userAccountInObligationGroupWithBonds = obligationGroupAccountService.getUserAccountInObligationGroupWithBonds(notExistingObligationGroupAccountId);
        assertEquals(HttpStatus.NOT_FOUND, userAccountInObligationGroupWithBonds.getStatusCode());
        // assertTrue(userAccountInObligationGroupWithBonds.getBody(). String);
    }

}
