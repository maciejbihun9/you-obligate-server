package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.GroupJoinRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maciej Bihun
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class GroupJoinRequestControllerIntegrationTest {

    @Autowired
    GroupJoinRequestController groupJoinRequestController;

    @Test
    @WithMockUser(username="maciek1", roles={"USER"})
    public void getGroupJoinRequestsByObligationGroupIdReturnsExceptionStatus(){
        int obligationGroupId = 5;
        ResponseEntity<List<GroupJoinRequest>> groupJoinRequestsByObligationGroupIdResponseEntity =
                groupJoinRequestController.getGroupJoinRequestsByObligationGroupId(obligationGroupId);
        HttpStatus statusCode = groupJoinRequestsByObligationGroupIdResponseEntity.getStatusCode();
        assertEquals(417, statusCode.value());
    }

}
