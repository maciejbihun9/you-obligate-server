package com.maciejbihun.service;

import com.maciejbihun.controller.impl.BondControllerImpl;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.impl.BondServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;

/**
 * @author Maciej Bihun
 */
@RunWith(MockitoJUnitRunner.class)
public class BondServiceTest {

    @Mock
    BondRepository bondRepository;

    @Mock
    UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Mock
    UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Mock
    CreatingMoneyStrategies creatingMoneyStrategies;

    private BondService bondService;

    @Before
    public void setUp(){
        this.bondService = new BondServiceImpl(bondRepository, obligationStrategyRepository, userAccountInObligationGroupRepository, creatingMoneyStrategies);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfPredictedAmountOfUnitsToPayExceedsTheLimit() throws Exception {
        Long userAccountInObligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 1001;

        // given
        Long id = 1L;

        User userMock = mock(User.class);
        ObligationGroup obligationGroup = new ObligationGroup(userMock, "", "", "", "");
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userMock, obligationGroup);

        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                mock(UserRegisteredService.class), obligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000);

        // when
        Mockito.when(obligationStrategyRepository.findById(id)).thenReturn(Optional.of(obligationStrategy));
        Mockito.when(userAccountInObligationGroupRepository.findById(id)).thenReturn(Optional.of(userAccountInObligationGroup));
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, amountOfUnitsToPay);

    }


}
