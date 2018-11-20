package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class BondServiceIntegrationTest {

    @Autowired
    private BondService bondService;

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfUserGroupAccountDoesNotExists() throws Exception {
        Long userAccountInObligationGroupId = -1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 1001;
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, amountOfUnitsToPay);
    }

}
