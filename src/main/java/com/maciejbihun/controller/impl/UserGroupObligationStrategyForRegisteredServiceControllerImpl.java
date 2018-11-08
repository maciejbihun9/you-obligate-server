package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional()
public class UserGroupObligationStrategyForRegisteredServiceControllerImpl implements UserGroupObligationStrategyForRegisteredServiceController {

    @Autowired
    UserService userService;

    @Override
    public UserGroupObligationStrategyForRegisteredService createObligationStrategy(String username, ObligationGroup obligationGroup) {
        UserPrincipal userPrincipal = userService.loadUserByUsername(username);
        User user = userPrincipal.getUser();
        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setUnitOfWork(UnitOfWork.SERVICE);
        obligationStrategy.setUnitOfWorkCost(new BigDecimal("100"));
        obligationStrategy.setObligationGroup(obligationGroup);
        obligationStrategy.setMaxAmountOfUnitsForObligation(100);

        UserRegisteredService exampleUserRegisteredService = user.getUserRegisteredServices().get(0);

        obligationStrategy.setUserRegisteredService(exampleUserRegisteredService);
        Hibernate.initialize(exampleUserRegisteredService.getUserGroupObligationStrategyForRegisteredServices());
        exampleUserRegisteredService.getUserGroupObligationStrategyForRegisteredServices().add(obligationStrategy);
        userService.saveUserData(user);

        return obligationStrategy;
    }

}
