package com.maciejbihun;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.*;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class ApplicationWorkflowTest {

    @Autowired
    UserService userService;

    @Autowired
    ObligationGroupService obligationGroupService;

    @Autowired
    UserGroupObligationStrategyForRegisteredServiceController userGroupObligationStrategyForRegisteredServiceController;

    @Test
    public void testApplicationWorkflow(){
        // load all users
        List<User> allUsers = userService.getAllUsers().getBody();
        User groupCreator = allUsers.get(0);

        ObligationGroup obligationGroup = new ObligationGroup();
        obligationGroup.setOwner(groupCreator);
        obligationGroup.setName("SPARTANS");
        obligationGroup.setDescription("We are not going to pay taxes anymore, bro !!!");
        obligationGroup.setMoneyName("BIHUN");
        obligationGroup.setMoneyShortcutName("BHN");

        // obligation group should be saved first
        obligationGroup = obligationGroupService.saveObligationGroup(obligationGroup);

        String username = allUsers.get(1).getUsername();
        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                userGroupObligationStrategyForRegisteredServiceController.createObligationStrategy(username, obligationGroup);

        // update user to save his registered service and strategy in the same time

        ResponseEntity<List<User>> allUsers1 = userService.getAllUsers();
        List<User> usersAfterChanges = allUsers1.getBody();
        User exampleUserWithSavedData = usersAfterChanges.get(1);
        System.out.println("Results");
        // add a registered service to a group
        /*allUsers.forEach(user -> {
            obligationGroup.getRegisteredServices().add(user.getUserRegisteredServices().get(0));
        });*/

    }

}
