package com.maciejbihun;

import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
// @WebMvcTest(UserRegisteredServiceController.class)
@ActiveProfiles("test")
public class ApplicationWorkflowTest {

    @Autowired
    UserService userService;

    @Test
    public void testApplicationWorkflow(){
        // load all users
        List<User> allUsers = userService.getAllUsers().getBody();
        User groupCreator = allUsers.get(0);

        ObligationGroup obligationGroup = new ObligationGroup();
        obligationGroup.setOwner(groupCreator);
        obligationGroup.setName("SPARTANS");
        obligationGroup.setDescription("We are not going to pay taxes anymore, bro !!!");

    }

}
