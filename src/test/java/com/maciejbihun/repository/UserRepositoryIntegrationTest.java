package com.maciejbihun.repository;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Maciej Bihun
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    // getUsersWithManyGroupAccounts() --- TEST METHODS --- //
    @Test
    public void getUsersWithManyGroupAccountsTest(){
        List<User> usersWithManyGroupAccounts = userRepository.getUsersWithManyGroupAccounts();
        int expectedAmountOfUsers = 20;
        assertEquals(expectedAmountOfUsers, usersWithManyGroupAccounts.size());
    }

}
