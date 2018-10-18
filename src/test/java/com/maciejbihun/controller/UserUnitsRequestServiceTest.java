package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserUnitsRequestServiceTest {

    /*@Before
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
    }*/

    @Test
    public void testMethod(){
        boolean test = true;
        assertTrue(test);
    }

}
