package com.maciejbihun.repository;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.models.UserUnitsRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class UserUnitsRequestRepositoryTest {

    @Autowired
    UserUnitsRequestRepository userUnitsRequestRepository;

    @Before
    public void init(){
        int i = 0;
        while(i < 20){
            UserUnitsRequest userUnitsRequest = new UserUnitsRequest();

            i++;
        }
    }

    @Test
    public void shouldReturnUserUnitsRequestFilteredByStatus(){

    }

}
