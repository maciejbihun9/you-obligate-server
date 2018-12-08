package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.ObligationGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
@Transactional
public class ObligationGroupServiceIntegrationTest {

    @Autowired
    private ObligationGroupService obligationGroupService;

    @Test
    public void shouldReturnObligationGroupWithRegisteredServicesTags(){
        // given
        Long obligationGroupId = 5L;



        ObligationGroup obligationGroupWithRegisteredServicesTags = obligationGroupService.getObligationGroupWithRegisteredServicesTags(obligationGroupId);
    }

    @Test
    public void getObligationGroupWithRegisteredServicesTags(){
        Long obligationGroupId = 5L;
        ObligationGroup obligationGroupWithRegisteredServicesTags =
                obligationGroupService.getObligationGroupWithRegisteredServicesTags(obligationGroupId);
    }

}
