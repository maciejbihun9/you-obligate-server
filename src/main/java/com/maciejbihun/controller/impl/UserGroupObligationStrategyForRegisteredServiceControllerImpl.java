package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupService;
import com.maciejbihun.controller.UserGroupObligationStrategyForRegisteredServiceController;
import com.maciejbihun.controller.UserRegisteredServiceController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.dto.UserDto;
import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional()
public class UserGroupObligationStrategyForRegisteredServiceControllerImpl implements UserGroupObligationStrategyForRegisteredServiceController {

    private static final String BAD_ARGUMENTS_WERE_PASSED_MESSAGE = "There was an exception associated with creating UserGroupObligationStrategyForRegisteredService object. \" +\n" +
            "                                \"Probably bad arguments were passed";

    @Autowired
    UserService userService;

    @Autowired
    ObligationGroupService obligationGroupService;

    @Autowired
    UserRegisteredServiceController userRegisteredServiceController;

    @Autowired
    UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public ResponseEntity<UserGroupObligationStrategyForRegisteredService> createObligationStrategy(UserGroupObligationStrategyForRegisteredServiceDto userGroupObligationStrategyForRegisteredServiceDto) {

        Session session = entityManager.unwrap(Session.class);

        // A user will pass an id of an obligation group and registered service to this method so, there is no need to create security for those next two lines.
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, userGroupObligationStrategyForRegisteredServiceDto.getObligationGroupDto().getId());

        UserRegisteredService userRegisteredService = session.load(UserRegisteredService.class, userGroupObligationStrategyForRegisteredServiceDto.getUserRegisteredServiceDto().getId());

        if (obligationGroup != null && userRegisteredService != null){
            try {
                // create new UserGroupObligationStrategyForRegisteredService
                UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                        userRegisteredService,
                        obligationGroup,
                        userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWork(),
                        userGroupObligationStrategyForRegisteredServiceDto.getUnitOfWorkCost(),
                        userGroupObligationStrategyForRegisteredServiceDto.getInterestRate(),
                        userGroupObligationStrategyForRegisteredServiceDto.getDebtUnitsLimit()
                );
                return new ResponseEntity<>(obligationStrategyRepository.save(obligationStrategy), HttpStatus.CREATED);
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
