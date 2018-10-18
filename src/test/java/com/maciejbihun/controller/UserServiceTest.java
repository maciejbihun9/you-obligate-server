package com.maciejbihun.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserService userService;

    List<User> users = new ArrayList<>();

    @Before
    public void init(){
        // create couple users
        int i = 0;
        while (i < 5){
            // store couple UserRegisteredService entities to verify id generation

            User user = new User();
            user.setName("Maciej " + i);
            user.setSurname("Bihun");
            user.setPassword("password");
            user.setLogin("login");

            List<UserRegisteredService> userRegisteredServices = new ArrayList<>();
            int j = 0;
            while(j < 5){
                UserRegisteredService userRegisteredService = new UserRegisteredService();
                userRegisteredService.setExperienceDescription("exp desc");
                userRegisteredService.setServiceDescription("Service desc");
                userRegisteredService.setServiceName("service name");
                userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
                userRegisteredService.setCreatedDateTime(LocalDateTime.now());
                userRegisteredServices.add(userRegisteredService);
                j++;
            }
            user.setUserRegisteredServices(userRegisteredServices);
            userService.saveUser(user);
            users.add(user);
            i++;
        }
    }

    @Test
    public void havingUserId_thereIsNoUserWithGivenId_statusNotFound(){
        Long testUserId = 14L;
        ResponseEntity<User> userEntity = userService.getUser(testUserId);
        assertEquals(userEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void userRegisteredServicesIdsAreGeneratedCorrectly(){
        Long id = 2L;
        List<Long> userRegisteredServicesIds = Arrays.asList(6L, 7L, 8L, 9L, 10L);
        ResponseEntity<User> userEntity = userService.getUser(id);
        assertNotNull("User with specified id is present", userEntity);
        List<Long> userEntityRegisteredServicesIds = userEntity.getBody().getUserRegisteredServices().stream().map(UserRegisteredService::getId).collect(toList());
        assertTrue(userRegisteredServicesIds.containsAll(userEntityRegisteredServicesIds));
    }

}
