package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.controller.UserRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.dto.UserDto;
import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    ObligationGroupService obligationGroupService;

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    @Autowired
    UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Override
    public UserGroupObligationStrategyForRegisteredService createObligationStrategy(UserGroupObligationStrategyForRegisteredServiceDto userGroupObligationStrategyForRegisteredServiceDto) {
        // create new UserGroupObligationStrategyForRegisteredService
        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService();
        obligationStrategy.setUnitOfWork(userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWork());
        obligationStrategy.setUnitOfWorkCost(userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWorkCost());

        ObligationGroup obligationGroup =
                obligationGroupService.loadObligationGroupById(userGroupObligationStrategyForRegisteredServiceDto.getObligationGroupDto().getId()).getBody();

        UserRegisteredService userRegisteredService =
                userRegisteredServiceController.getUserRegisteredService(userGroupObligationStrategyForRegisteredServiceDto.getUserRegisteredServiceDto().getId()).getBody();

        obligationStrategy.setObligationGroup(obligationGroup);
        obligationStrategy.setUserRegisteredService(userRegisteredService);

        obligationStrategy.setMaxAmountOfUnitsForObligation(userGroupObligationStrategyForRegisteredServiceDto.getMaxAmountOfUnitsForObligation());

        // obligationStrategy is a child of a userRegisteredService with one-to-many association,
        // so saving user I will createBondInObligationGroup all the data
        /* userRegisteredService.getUserGroupObligationStrategyForRegisteredServices().add(obligationStrategy);
        userService.saveUserData(userRegisteredService.getUser()); */
        return obligationStrategyRepository.save(obligationStrategy);
    }

}
