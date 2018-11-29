package com.maciejbihun;

import com.maciejbihun.controller.*;
import com.maciejbihun.dto.*;
import com.maciejbihun.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class ApplicationWorkflowTest {

    @Autowired
    UserController userController;

    @Autowired
    ObligationGroupController obligationGroupController;

    @Autowired
    BondController bondController;

    @Autowired
    RegisteredServiceObligationStrategyController registeredServiceObligationStrategyController;

    @Autowired
    UserAccountInObligationGroupController obligationGroupAccountService;


    @Test
    public void testApplicationWorkflow(){
        // load all users
        List<User> allUsers = userController.getAllUsers().getBody();
        User groupCreator = allUsers.get(0);

        ObligationGroup obligationGroup = new ObligationGroup(groupCreator,"SPARTANS", "BIHUN", "BHN", "desc");

        // obligation group should be saved first
        obligationGroup = obligationGroupController.saveObligationGroup(obligationGroup);

        User user = allUsers.get(1);

        UserAccountInObligationGroupDto userAccountInObligationGroupDto = new UserAccountInObligationGroupDto(user.getUsername(), obligationGroup.getId());
        ResponseEntity<UserAccountInObligationGroup> userObligationGroupAccount = obligationGroupAccountService.createGroupAccount(userAccountInObligationGroupDto);

        UserGroupObligationStrategyForRegisteredServiceDto obligationStrategyDto =
                new UserGroupObligationStrategyForRegisteredServiceDto();
        obligationStrategyDto.setAlreadyCreatedAmountOfMoney(new BigDecimal("0.00"));
        obligationStrategyDto.setAlreadyObligatedUnitsOfWork(0);
        obligationStrategyDto.setInterestRate(new BigDecimal("0.00"));
        obligationStrategyDto.setAmountOfUnitsEverPaid(0);
        obligationStrategyDto.setMaxAmountOfUnitsForObligation(100);
        obligationStrategyDto.setUnitOfWorkCost(new BigDecimal("100.00"));

        ObligationGroupDto obligationGroupDto = new ObligationGroupDto();
        obligationGroupDto.setId(obligationGroup.getId());
        obligationStrategyDto.setObligationGroupDto(obligationGroupDto);

        UserRegisteredServiceDto userRegisteredServiceDto = new UserRegisteredServiceDto();
        userRegisteredServiceDto.setId(user.getUserRegisteredServices().get(0).getId());
        obligationStrategyDto.setUserRegisteredServiceDto(userRegisteredServiceDto);

        ResponseEntity<RegisteredServiceObligationStrategy> obligationStrategy =
                registeredServiceObligationStrategyController.createObligationStrategy(obligationStrategyDto);

        // create bond object between a user and group:
        BondDto dentistBondDto = new BondDto(100, obligationStrategy.getBody().getId(), userObligationGroupAccount.getBody().getId());
        dentistBondDto.setAmountOfUnitsToPay(100);
        ResponseEntity<Bond> bondInObligationGroup = bondController.createBondInObligationStrategy(dentistBondDto);
        System.out.println();

        // create obligation strategy for each registered service
        // define an obligation strategy for each registered service itself
        // create an obligation strategy for each user registered service without an admin

        // allow a user to obligate

    }

}
