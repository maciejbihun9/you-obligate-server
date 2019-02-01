package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.exceptions.NotEnoughPermissionsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maciej Bihun
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class GroupJoinRequestServiceTest {

    @Autowired
    GroupJoinRequestService groupJoinRequestService;

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username="maciek1", roles={"USER"})
    public void getGroupJoinRequestsByObligationGroupIdMethodCallIsAllowedByAdmin(){
        int obligationGroupId = 1;
        try {
            groupJoinRequestService.getGroupJoinRequestsByObligationGroupId(obligationGroupId);
        } catch (NotEnoughPermissionsException e) {
            e.printStackTrace();
        }
    }

}
