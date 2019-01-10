package com.maciejbihun.repository;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class ObligationGroupRepositoryIntegrationTest {

    @Autowired
    ObligationGroupRepository obligationGroupRepository;

    @Test
    public void getObligationGroupWithRegisteredServices(){
        Long obligationGroupId = 2L;
        ObligationGroup obligationGroupWithRegisteredServices = obligationGroupRepository.getObligationGroupWithRegisteredServices(obligationGroupId);
        Long id = obligationGroupWithRegisteredServices.getId();
        assertEquals(2L, id.longValue());
        assertNotNull(obligationGroupWithRegisteredServices.getUserAccountsInObligationGroup().iterator().next().getUserObligationStrategies()
                .iterator().next().getUserRegisteredService());
    }

}
