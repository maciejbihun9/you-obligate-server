package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    @WithMockUser(username="maciek1",roles={"USER"})
    public void shouldReturnCorrectLoggedInUser(){
        ResponseEntity<UserDto> loggedInUser = userController.getLoggedInUser();
        assertEquals("maciek1", loggedInUser.getBody().getUsername());
    }

    /*@Before
    public void init(){
        // create couple users
        int i = 0;
        while (i < 5){
            // store couple UserRegisteredService entities to verify id generation

            User user = new User();
            user.setName("Maciej " + i);
            user.setSurname("Bihun");
            user.setPassword("password");
            user.setUsername("login" + i);

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
            userController.createUserAccount(user);
            users.add(user);
            i++;
        }
    }*/

  /*  @Test
    public void getUserTest(){
        UserPrincipal maciek1 = userController.loadUserByUsername("maciek1");
        Collection<Role> roles = maciek1.getUser().getRoles();
        System.out.println(roles);
    }*/

    /*@Test
    public void userPasswordShouldHave60OfLength(){
        UserDto userDto = new UserDto();
        userDto.setName("Maciej");
        userDto.setSurname("Bihun");
        userDto.setPassword("maciek");
        // make sure that this username is different than in the InitialDataLoader (maciek1) class, because
        // you will end up with ConstraintViolationException
        userDto.setUsername("maciek");
        ResponseEntity<User> userAccount = userController.createUserAccount(userDto);
        assertEquals(60 ,userAccount.getBody().getPassword().length());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void havingUsername_thereIsNoUserWithGivenUsername_throwsException(){
        String testUsername = "some username";
        userController.loadUserByUsername(testUsername);
    }*/

    /*@Test
    public void userRegisteredServicesIdsAreGeneratedCorrectly(){
        String username = "maciek1";
        List<Long> userRegisteredServicesIds = Arrays.asList(6L, 7L, 8L, 9L, 10L);
        UserPrincipal userEntity = userController.loadUserByUsername(username);
        assertNotNull("User with specified id is present", userEntity);
        List<Long> userEntityRegisteredServicesIds = userEntity.getUser().getUserRegisteredServices().stream().map(UserRegisteredService::getId).collect(toList());
        assertTrue(userRegisteredServicesIds.containsAll(userEntityRegisteredServicesIds));
        System.out.println(userEntityRegisteredServicesIds);
    }*/

}
