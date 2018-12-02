package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserAccountInObligationGroupServiceIntegrationTest {

    @Autowired
    private UserAccountInObligationGroupService userAccountInObligationGroupService;

    // getUserAccountWithObligationStrategies() --- TEST METHODS --- //
    /*@Test
    public void obligationStrategiesShouldBeInitialized(){
        // given
        Long userAccountId = 1L;

        // when
        UserAccountInObligationGroup userAccountWithObligationStrategies = userAccountInObligationGroupService.getUserAccountWithObligationStrategies(userAccountId);

        // then
        assertNotNull(userAccountWithObligationStrategies.getUserObligationStrategies().get(0));

    }*/

}
