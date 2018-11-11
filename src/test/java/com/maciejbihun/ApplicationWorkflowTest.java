package com.maciejbihun;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.*;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    UserGroupObligationStrategyForRegisteredServiceController userGroupObligationStrategyForRegisteredServiceController;

    @Test
    public void testApplicationWorkflow(){
        // load all users
        List<User> allUsers = userService.getAllUsers().getBody();
        User groupCreator = allUsers.get(0);

        ObligationGroup obligationGroup = new ObligationGroup();
        obligationGroup.setOwner(groupCreator);
        obligationGroup.setName("SPARTANS");
        obligationGroup.setDescription("We are not going to pay taxes anymore, bro !!!");
        obligationGroup.setMoneyName("BIHUN");
        obligationGroup.setMoneyShortcutName("BHN");

        // obligation group should be saved first
        obligationGroup = obligationGroupService.saveObligationGroup(obligationGroup);

        User user = allUsers.get(1);

        UserGroupObligationStrategyForRegisteredServiceDto obligationStrategyDto =
                new UserGroupObligationStrategyForRegisteredServiceDto();
        obligationStrategyDto.setAlreadyCreatedAmountOfMoney(new BigDecimal("0.00"));
        obligationStrategyDto.setAlreadyObligatedUnitsOfWork(0);
        obligationStrategyDto.setInterestRate(new BigDecimal("0.00"));
        obligationStrategyDto.setAmountOfUnitsEverPaid(0);
        obligationStrategyDto.setMaxAmountOfUnitsForObligation(100);

        ObligationGroupDto obligationGroupDto = new ObligationGroupDto();
        obligationGroupDto.setId(obligationGroup.getId());
        obligationStrategyDto.setObligationGroupDto(obligationGroupDto);

        UserRegisteredServiceDto userRegisteredServiceDto = new UserRegisteredServiceDto();
        userRegisteredServiceDto.setId(user.getUserRegisteredServices().get(0).getId());
        obligationStrategyDto.setUserRegisteredServiceDto(userRegisteredServiceDto);

        UserGroupObligationStrategyForRegisteredService obligationStrategy =
                userGroupObligationStrategyForRegisteredServiceController.createObligationStrategy(obligationStrategyDto);

        // create bond object between a user and group:
        Bond dentistBond = new Bond();


        // create obligation strategy for each registered service
        // define an obligation strategy for each registered service itself
        // create an obligation strategy for each user registered service without an admin

        // allow a user to obligate

    }

}
