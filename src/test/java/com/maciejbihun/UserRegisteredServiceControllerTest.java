package com.maciejbihun;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.controllers.UserRegisteredServiceController;
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
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserRegisteredServiceControllerTest {

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    private List<UserRegisteredService> allUserRegisteredServices;

    @Before
    public void init(){
        int i = 0;
        while(i < 150){
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setExperienceDescription("exp desc");
            userRegisteredService.setServiceDescription("Service desc");
            userRegisteredService.setServiceName("service name");
            userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);
            i++;
        }
        allUserRegisteredServices = userRegisteredServiceController.getAllUserRegisteredServices();
        System.out.println(allUserRegisteredServices);
    }

    @Test
    public void getUserRegisteredServiceTest(){
        Long testId = 1500L;
        ResponseEntity<UserRegisteredService> notExistingUserRegisteredService = userRegisteredServiceController.getUserRegisteredService(testId);
        assertNull(notExistingUserRegisteredService.getBody());
        assertEquals(notExistingUserRegisteredService.getStatusCode(), HttpStatus.NOT_FOUND);

        Long existingId = 10L;
        ResponseEntity<UserRegisteredService> existingUserRegisteredService = userRegisteredServiceController.getUserRegisteredService(existingId);
        assertEquals(existingUserRegisteredService.getStatusCode(), HttpStatus.OK);
        assertNotNull(existingUserRegisteredService);
    }

    @Test
    public void deleteUserRegisteredServiceTest(){
        Long notExistingUserServiceId = 1500l;
        UserRegisteredService userRegisteredService = new UserRegisteredService();
        userRegisteredService.setExperienceDescription("exp desc");
        userRegisteredService.setServiceDescription("Service desc");
        userRegisteredService.setServiceName("service name");
        userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);

        ResponseEntity<String> notFoundStatusResponseEntity = userRegisteredServiceController.deleteUserRegisteredService(notExistingUserServiceId);
        assertEquals(notFoundStatusResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

        userRegisteredService = userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);
        assertNotNull(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()));

        ResponseEntity<String> noContentResponseEntity = userRegisteredServiceController.deleteUserRegisteredService(userRegisteredService.getId());
        assertEquals(noContentResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertNull(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()));

    }

    @Test
    public void testSavingItem(){
        UserRegisteredService userRegisteredService = new UserRegisteredService();
        userRegisteredService.setExperienceDescription("exp desc");
        userRegisteredService.setServiceDescription("Service desc");
        userRegisteredService.setServiceName("service name");
        userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
        UserRegisteredService savedUserRegisteredService = userRegisteredServiceController.saveUserRegisteredService(userRegisteredService);
        System.out.println("Testing saving user registered service");
        assertEquals(userRegisteredService, savedUserRegisteredService);
    }


}
