package com.maciejbihun;

import com.maciejbihun.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
// @WebMvcTest(UserRegisteredServiceController.class)
@ActiveProfiles("test")
public class ApplicationWorkflowTest {

    @Test
    public void testApplicationWorkflow(){

    }

}
