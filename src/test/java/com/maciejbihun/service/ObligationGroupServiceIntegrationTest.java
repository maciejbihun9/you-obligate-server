package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
@Transactional
public class ObligationGroupServiceIntegrationTest {

    @Autowired
    private ObligationGroupService obligationGroupService;

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnObligationGroupWithRegisteredServicesTags(){
        // given
        Long obligationGroupId = 5L;

        obligationGroupService.getObligationGroupsWithRegisteredServicesTags();
    }

    @Test
    public void getObligationGroupWithRegisteredServicesTags(){
        Long obligationGroupId = 5L;
        obligationGroupService.getObligationGroupsWithRegisteredServicesTags();
    }

    @Test
    public void recommendObligationGroupsForUser(){
        UserPrincipal user3 = userService.loadUserByUsername("user3");
        User user = user3.getUser();
        int i = 0;
        while(i < 7){
            ServiceTag serviceTag = new ServiceTag();
            serviceTag.setValue("TAG" + i);
            user.getExpectedServicesTags().add(serviceTag);
            i++;
        }
        userService.saveUser(user);
        // List<ObligationGroup> obligationGroups = obligationGroupService.getRecommendObligationGroups(user);
    }

}
