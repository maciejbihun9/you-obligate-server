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
import java.util.Optional;

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
        userRegisteredService.setCreatedDateTime(LocalDateTime.now());
        userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.HEALTH);
        userRegisteredService.setServiceName("dsafa");
        userRegisteredService.setExperienceDescription("dsafads");
        userRegisteredService.setServiceDescription("fdasfdas");

        UserUnitsRequest userUnitsRequest = new UserUnitsRequest();
        userUnitsRequest.setCreatedDateTime(LocalDateTime.now());
        userUnitsRequest.setUserRegisteredServiceId(userRegisteredService);

        userRegisteredService.setUserUnitsRequest(userUnitsRequest);

        userRegisteredService = userRegisteredServiceRepository.save(userRegisteredService);

        Optional<UserRegisteredService> userRegisteredServiceOptional = userRegisteredServiceRepository.findById(userRegisteredService.getId());
        assertTrue(userRegisteredServiceOptional.isPresent());
        userUnitsRequest = userRegisteredServiceOptional.get().getUserUnitsRequest();
        assertNotNull(userUnitsRequest);
    }

}
