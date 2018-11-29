package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.RegisteredServiceObligationStrategyController;
import com.maciejbihun.dto.*;
import com.maciejbihun.models.*;
import com.maciejbihun.service.RegisteredServiceObligationStrategyService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;

@Controller
public class RegisteredServiceObligationStrategyControllerImpl implements RegisteredServiceObligationStrategyController {

    private static final String BAD_ARGUMENTS_WERE_PASSED_MESSAGE = "There was an exception associated with creating RegisteredServiceObligationStrategy object. \" +\n" +
            "                                \"Probably bad arguments were passed";

    @Autowired
    EntityManager entityManager;

    @Autowired
    RegisteredServiceObligationStrategyService registeredServiceObligationStrategyService;

    @Override
    public ResponseEntity<RegisteredServiceObligationStrategy> createObligationStrategy(UserGroupObligationStrategyForRegisteredServiceDto
                                                                                                    userGroupObligationStrategyForRegisteredServiceDto) {

        Session session = entityManager.unwrap(Session.class);

        // A user will pass an id of an obligation group and registered service to this method so, there is no need to create security for those next two lines.
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, userGroupObligationStrategyForRegisteredServiceDto.getObligationGroupDto().getId());

        UserRegisteredService userRegisteredService = session.load(UserRegisteredService.class, userGroupObligationStrategyForRegisteredServiceDto.getUserRegisteredServiceDto().getId());

        UserAccountInObligationGroup userAccountInObligationGroup = session.load(UserAccountInObligationGroup.class, userGroupObligationStrategyForRegisteredServiceDto.getUserRegisteredServiceDto().getId());

        if (obligationGroup != null && userRegisteredService != null){
            try {
                // create new RegisteredServiceObligationStrategy
                RegisteredServiceObligationStrategy obligationStrategy = registeredServiceObligationStrategyService.createObligationStrategy(
                        userRegisteredService,
                        userAccountInObligationGroup,
                        userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWork(),
                        userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWorkCost(),
                        userGroupObligationStrategyForRegisteredServiceDto.getInterestRate(),
                        userGroupObligationStrategyForRegisteredServiceDto.getDebtUnitsLimit());
                return new ResponseEntity<>(obligationStrategy, HttpStatus.CREATED);
            } catch (Exception e){
                MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
                multiValueMap.set("error", BAD_ARGUMENTS_WERE_PASSED_MESSAGE);
                return new ResponseEntity<>(multiValueMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("error", BAD_ARGUMENTS_WERE_PASSED_MESSAGE);
            return new ResponseEntity<>(multiValueMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
