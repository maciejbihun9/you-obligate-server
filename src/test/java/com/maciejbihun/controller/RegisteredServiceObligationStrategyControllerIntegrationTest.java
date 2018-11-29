package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class RegisteredServiceObligationStrategyControllerIntegrationTest {

    @Autowired
    RegisteredServiceObligationStrategyController registeredServiceObligationStrategyController;

    @Autowired
    ObligationGroupController obligationGroupService;

    @Autowired
    UserRegisteredServiceRepository userRegisteredServiceRepository;

    @Test
    public void createObligationStrategyTest(){

        // given

        Long userRegisteredServiceId = 1L;
        Long obligationGroupId = 1L;
        int debtUnitsLimit = 1000;

        UserGroupObligationStrategyForRegisteredServiceDto userGroupObligationStrategyForRegisteredServiceDto =
                new UserGroupObligationStrategyForRegisteredServiceDto();
        userGroupObligationStrategyForRegisteredServiceDto.setUnitOfWorkCost(new BigDecimal("100.00"));
        userGroupObligationStrategyForRegisteredServiceDto.setUnitOfWork(UnitOfWork.SERVICE);

        UserRegisteredServiceDto userRegisteredServiceDto = new UserRegisteredServiceDto();
        userRegisteredServiceDto.setId(userRegisteredServiceId);

        ObligationGroupDto obligationGroupDto = new ObligationGroupDto();
        obligationGroupDto.setId(obligationGroupId);

        userGroupObligationStrategyForRegisteredServiceDto.setUnitOfWork(UnitOfWork.SERVICE);
        userGroupObligationStrategyForRegisteredServiceDto.setInterestRate(new BigDecimal("0.05"));
        userGroupObligationStrategyForRegisteredServiceDto.setUnitOfWorkCost(BigDecimal.valueOf(10000,2));
        userGroupObligationStrategyForRegisteredServiceDto.setUserRegisteredServiceDto(userRegisteredServiceDto);
        userGroupObligationStrategyForRegisteredServiceDto.setObligationGroupDto(obligationGroupDto);
        userGroupObligationStrategyForRegisteredServiceDto.setDebtUnitsLimit(debtUnitsLimit);

        ResponseEntity<RegisteredServiceObligationStrategy> obligationStrategyResponseEntity =
                registeredServiceObligationStrategyController.createObligationStrategy(userGroupObligationStrategyForRegisteredServiceDto);
    }

}
