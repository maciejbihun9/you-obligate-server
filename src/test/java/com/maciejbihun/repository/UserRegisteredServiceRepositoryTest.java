package com.maciejbihun.repository;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.models.UserUnitsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserRegisteredServiceRepositoryTest {

    @Autowired
    UserRegisteredServiceRepository userRegisteredServiceRepository;

    @Test
    public void annotationsInEntitiesAreWellDefined(){
        UserRegisteredService userRegisteredService = new UserRegisteredService();
        userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.HEALTH);
        userRegisteredService.setServiceName("dsafa");
        userRegisteredService.setExperienceDescription("dsafads");
        userRegisteredService.setServiceDescription("fdasfdas");

        UserUnitsRequest userUnitsRequest = new UserUnitsRequest();
        userUnitsRequest.setCreatedDateTime(LocalDateTime.now());
        userUnitsRequest.setUserRegisteredService(userRegisteredService);

        userRegisteredService = userRegisteredServiceRepository.save(userRegisteredService);

        Optional<UserRegisteredService> userRegisteredServiceOptional = userRegisteredServiceRepository.findById(userRegisteredService.getId());
        assertTrue(userRegisteredServiceOptional.isPresent());
        assertNotNull(userUnitsRequest);
    }

    @Test
    public void givenListOfUserRegisteredServices_filterByCategory_returnsItServices(){
        int i = 0;
        while(i < 50){
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setExperienceDescription("exp desc");
            userRegisteredService.setServiceDescription("Service desc");
            userRegisteredService.setServiceName("service name");
            if (i % 2 == 0){
                userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            } else {
                userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.LEARNING);
            }

            UserUnitsRequest userUnitsRequest = new UserUnitsRequest();
            userUnitsRequest.setUserRegisteredService(userRegisteredService);
            userUnitsRequest.setCreatedDateTime(LocalDateTime.now());
            userRegisteredServiceRepository.save(userRegisteredService);
            i++;
        }
        List<UserRegisteredService> itServices = userRegisteredServiceRepository.findByUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
        List<UserRegisteredServiceCategory> categories = itServices.stream().map(UserRegisteredService::getUserRegisteredServiceCategory).collect(toList());
        assertTrue(categories.stream().allMatch(category -> category.equals(UserRegisteredServiceCategory.IT)));
    }

}
