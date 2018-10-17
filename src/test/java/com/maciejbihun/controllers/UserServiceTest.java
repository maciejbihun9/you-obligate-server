package com.maciejbihun.controllers;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    List<User> users = new ArrayList<>();

    @Before
    public void init(){
        // create couple users
        int i = 0;
        while (i < 5){
            // store couple UserRegisteredService entities to verify id generation
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setExperienceDescription("exp desc");
            userRegisteredService.setServiceDescription("Service desc");
            userRegisteredService.setServiceName("service name");
            userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);

            User user = new User();
            user.setName("Maciej " + i);
            user.setSurname("Bihun");
            user.setPassword("password");
            user.setLogin("login");
            userService.saveUser(user);
            users.add(user);
            i++;
        }
    }

    @Test
    public void userIdGenerationTest(){
        Long id = 3L;
        ResponseEntity<User> userEntity = userService.getUser(id);
        assertNotNull(userEntity);
    }


}
