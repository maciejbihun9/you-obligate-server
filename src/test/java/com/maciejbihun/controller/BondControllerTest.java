package com.maciejbihun.controller;

import com.maciejbihun.controller.impl.BondControllerImpl;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.RegisteredServiceObligationStrategyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Maciej Bihun
 */
@RunWith(MockitoJUnitRunner.class)
public class BondControllerTest {

    @Mock
    BondRepository bondRepository;

    @Mock
    RegisteredServiceObligationStrategyRepository obligationStrategyRepository;

    @Mock
    UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    private BondController bondController;

    @Before
    public void setUp(){
        this.bondController = new BondControllerImpl(bondRepository, obligationStrategyRepository, userAccountInObligationGroupRepository);
    }
    // test a method without database

    @Test
    public void Should_ReturnHttpStatusNotFound_When_ObligationGroupDoesNotExists(){
        Long obligationStrategyId = 10L;
        Mockito.when(obligationStrategyRepository.findById(obligationStrategyId)).thenReturn(Optional.empty());
        Mockito.when(userAccountInObligationGroupRepository.findById(obligationStrategyId)).thenReturn(Optional.empty());

        BondDto bondDto = new BondDto(100, 10L, 10L);

        ResponseEntity<Bond> bondInObligationGroup = bondController.createBondInObligationStrategy(bondDto);
        assertEquals(HttpStatus.NOT_FOUND, bondInObligationGroup.getStatusCode());
    }

    // block creating new bonds when limit has been reached
    // what should happen when
    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfDeptIsOverTheLimit(){
        // given
        Long id = 1L;

        User userMock = mock(User.class);
        BondDto bondDto = new BondDto(1001, id, id);
        ObligationGroup obligationGroup = new ObligationGroup(userMock, "", "", "", "");
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userMock, obligationGroup);

        RegisteredServiceObligationStrategy obligationStrategy = new RegisteredServiceObligationStrategy(
                mock(UserRegisteredService.class), userAccountInObligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"),2,  1000);

        // when
        Mockito.when(obligationStrategyRepository.findById(id)).thenReturn(Optional.of(obligationStrategy));
        Mockito.when(userAccountInObligationGroupRepository.findById(id)).thenReturn(Optional.of(userAccountInObligationGroup));
        bondController.createBondInObligationStrategy(bondDto);
    }

}
