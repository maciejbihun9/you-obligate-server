package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserRegisteredServiceControllerTest {

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    @Before
    public void init(){
        int i = 0;
        while(i < 150){
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setExperienceDescription("exp desc");
            userRegisteredService.setServiceDescription("Service desc");
            userRegisteredService.setServiceName("service name");
            userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            userRegisteredService.setCreatedDateTime(LocalDateTime.now());
            userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);
            i++;
        }
    }

    @Test
    public void userRegisteredServiceEntityShouldReturnCorrectStatus(){
        Long notExistingId = 1500L;
        ResponseEntity<UserRegisteredService> notExistingUserRegisteredService = userRegisteredServiceController.getUserRegisteredService(notExistingId);
        assertNull(notExistingUserRegisteredService.getBody());
        assertEquals(notExistingUserRegisteredService.getStatusCode(), HttpStatus.NOT_FOUND);

        Long existingId = 10L;
        ResponseEntity<UserRegisteredService> existingUserRegisteredService = userRegisteredServiceController.getUserRegisteredService(existingId);
        assertEquals(existingUserRegisteredService.getStatusCode(), HttpStatus.FOUND);
        assertNotNull(existingUserRegisteredService);
    }

    @Test
    public void shouldReturnCorrectStatusAfterDeletion(){
        Long notExistingUserServiceId = 1500L;
        UserRegisteredService userRegisteredService = new UserRegisteredService();
        userRegisteredService.setExperienceDescription("exp desc");
        userRegisteredService.setServiceDescription("Service desc");
        userRegisteredService.setServiceName("service name");
        userRegisteredService.setCreatedDateTime(LocalDateTime.now());
        userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);

        ResponseEntity<String> notFoundStatusResponseEntity = userRegisteredServiceController.deleteUserRegisteredService(notExistingUserServiceId);
        assertEquals(notFoundStatusResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

        userRegisteredService = userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);
        assertNotNull(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()));

        ResponseEntity<String> noContentResponseEntity = userRegisteredServiceController.deleteUserRegisteredService(userRegisteredService.getId());
        assertEquals(noContentResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()).getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
