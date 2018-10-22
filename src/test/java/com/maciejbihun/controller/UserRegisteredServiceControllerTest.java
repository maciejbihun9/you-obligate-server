package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
// @WebMvcTest(UserRegisteredServiceController.class)
@ActiveProfiles("test")
public class UserRegisteredServiceControllerTest {

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    @Autowired
    UserService userService;

    @Before
    public void init(){
        int i = 0;
        while(i < 150){
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setExperienceDescription("exp desc");
            userRegisteredService.setServiceDescription("Service desc");
            userRegisteredService.setServiceName("service name");
            if (i % 2 == 0){
                userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            } else {
                userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.LEARNING);
            }
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

        userRegisteredService = userRegisteredServiceController.saveUserRegisteredService(userRegisteredService).getBody();
        assertNotNull(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()));

        ResponseEntity<String> noContentResponseEntity = userRegisteredServiceController.deleteUserRegisteredService(userRegisteredService.getId());
        assertEquals(noContentResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
        assertEquals(userRegisteredServiceController.getUserRegisteredService(userRegisteredService.getId()).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username="maciek1",roles={"USER"})
    public void shouldThrowAccessDeniedExceptionWhenAccessingWithSimpleUser(){
        String learningCategory = "LEARNING";
        ResponseEntity<List<UserRegisteredService>> byUserRegisteredServiceCategory = userRegisteredServiceController.getUserRegisteredServices(learningCategory);
        assertEquals(HttpStatus.FOUND, byUserRegisteredServiceCategory.getStatusCode());
    }

    @Test
    @WithMockUser(username="maciek1",roles={"ADMIN"})
    public void shouldFilterByUserRegisteredServiceLearningCategory(){
        String learningCategory = "LEARNING";
        ResponseEntity<List<UserRegisteredService>> byUserRegisteredServiceCategory = userRegisteredServiceController.getUserRegisteredServices(learningCategory);
        assertEquals(HttpStatus.FOUND, byUserRegisteredServiceCategory.getStatusCode());
    }

    @Test
    @WithMockUser(username="maciek1",roles={"ADMIN"})
    public void shouldReturnAllUserRegisteredServices(){
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseEntity<List<UserRegisteredService>> userRegisteredServices = userRegisteredServiceController.getUserRegisteredServices(null);
        assertEquals(150, ((List<UserRegisteredService>)userRegisteredServices.getBody()).size());

        ResponseEntity<List<UserRegisteredService>> userRegisteredServicesByLearning =
                userRegisteredServiceController.getUserRegisteredServices(UserRegisteredServiceCategory.LEARNING.getCategory());

    }

}
