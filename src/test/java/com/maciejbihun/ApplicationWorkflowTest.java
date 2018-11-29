package com.maciejbihun;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.*;
import com.maciejbihun.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class ApplicationWorkflowTest {

    @Autowired
    UserService userService;

    @Autowired
    ObligationGroupService obligationGroupService;

    @Autowired
    BondService bondService;

    @Autowired
    RegisteredServiceObligationStrategyService registeredServiceObligationStrategyService;

    @Autowired
    UserAccountInObligationGroupService obligationGroupAccountService;


    @Test
    public void testApplicationWorkflow(){
        // load all users
        List<User> allUsers = userService.getAllUsers();
        User groupCreator = allUsers.get(0);

        ObligationGroup obligationGroup = new ObligationGroup(groupCreator,"SPARTANS", "BIHUN", "BHN", "desc");

        // obligation group should be saved first
        obligationGroup = obligationGroupService.saveObligationGroup(obligationGroup);

        User user = allUsers.get(1);

        UserAccountInObligationGroup userAccountInObligationGroup = null;
        try {
            userAccountInObligationGroup = obligationGroupAccountService.createUserAccountInObligationGroup(user.getUsername(), obligationGroup.getId());
        } catch (ObligationGroupDoesNotExistsException e) {
            e.printStackTrace();
        }

        RegisteredServiceObligationStrategy obligationStrategy =
                new RegisteredServiceObligationStrategy(
                        user.getUserRegisteredServices().get(0),
                        userAccountInObligationGroup,
                        UnitOfWork.SERVICE,
                        new BigDecimal("100.00"),
                        new BigDecimal("0.00"),
                        2,
                        100
                );
        obligationStrategy = registeredServiceObligationStrategyService.saveObligationStrategy(obligationStrategy);

        // create bond object between a user and group:
        try {
            Bond bondInObligationStrategy = bondService.createBondInObligationStrategy(obligationStrategy.getId(), 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create obligation strategy for each registered service
        // define an obligation strategy for each registered service itself
        // create an obligation strategy for each user registered service without an admin

        // allow a user to obligate

    }

}
