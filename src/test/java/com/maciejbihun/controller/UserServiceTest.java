package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    UserService userService;

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
            userService.createUserAccount(user);
            users.add(user);
            i++;
        }
    }*/

    @Test
    public void getUserTest(){
        UserPrincipal maciek1 = userService.loadUserByUsername("maciek1");
        Collection<Role> roles = maciek1.getUser().getRoles();
        System.out.println(roles);
    }

    @Test
    public void userPasswordShouldHave60OfLength(){
        User user = new User();
        user.setName("Maciej");
        user.setSurname("Bihun");
        user.setPassword("maciek1");
        user.setUsername("maciek1");
        ResponseEntity<User> userAccount = userService.createUserAccount(user);
        assertEquals(60 ,userAccount.getBody().getPassword().length());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void havingUsername_thereIsNoUserWithGivenUsername_throwsException(){
        String testUsername = "some username";
        UserDetails userDetails = userService.loadUserByUsername(testUsername);
    }

    /*@Test
    public void userRegisteredServicesIdsAreGeneratedCorrectly(){
        String username = "login1";
        List<Long> userRegisteredServicesIds = Arrays.asList(6L, 7L, 8L, 9L, 10L);
        UserPrincipal userEntity = userService.loadUserByUsername(username);
        assertNotNull("User with specified id is present", userEntity);
        List<Long> userEntityRegisteredServicesIds = userEntity.getUser().getUserRegisteredServices().stream().map(UserRegisteredService::getId).collect(toList());
        assertTrue(userRegisteredServicesIds.containsAll(userEntityRegisteredServicesIds));
        System.out.println(userEntityRegisteredServicesIds);
    }*/

}
