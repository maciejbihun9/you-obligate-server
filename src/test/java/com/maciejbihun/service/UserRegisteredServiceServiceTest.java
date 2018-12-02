package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.models.UserRegisteredService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
@Transactional
public class UserRegisteredServiceServiceTest {

    @Autowired
    UserRegisteredServiceService userRegisteredServiceService;

    @Autowired
    UserService userService;

    @Test
    public void shouldReturnRegisteredServiceTerms(){
        // given
        Long userRegisteredServiceId = 2L;
        Optional<UserRegisteredService> userRegisteredServiceOptional = userRegisteredServiceService.getUserRegisteredService(userRegisteredServiceId);
        UserRegisteredService userRegisteredService = userRegisteredServiceOptional.get();
        userRegisteredService.getRegisteredServiceTerms().add("term 1");
        userRegisteredService.getRegisteredServiceTerms().add("term 2");
        userRegisteredService.getRegisteredServiceTerms().add("term 3");

        // when
        userRegisteredService = userRegisteredServiceService.saveUserRegisteredService(userRegisteredService);
        userRegisteredServiceOptional = userRegisteredServiceService.getUserRegisteredService(userRegisteredServiceId);
        userRegisteredService = userRegisteredServiceOptional.get();

        // then
        assertEquals(3, userRegisteredService.getRegisteredServiceTerms().size());

        // given
        List<String> userRegisteredServiceTerms = new ArrayList<>(Arrays.asList("term 4", "term 5", "term 6", "term 7"));
        userRegisteredService.setRegisteredServiceTerms(userRegisteredServiceTerms);

        // when
        userRegisteredService = userRegisteredServiceService.saveUserRegisteredService(userRegisteredService);
        userRegisteredServiceOptional = userRegisteredServiceService.getUserRegisteredService(userRegisteredServiceId);
        userRegisteredService = userRegisteredServiceOptional.get();

        // then

        assertEquals(4, userRegisteredService.getRegisteredServiceTerms().size());
        assertEquals("term 4", userRegisteredService.getRegisteredServiceTerms().get(0));
    }



}
