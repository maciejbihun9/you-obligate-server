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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        UserRegisteredService userRegisteredService = allUserRegisteredServices.get(1);
        Long id = userRegisteredService.getId();
        assertNotNull(userRegisteredServiceController.getUserRegisteredService(id));
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
