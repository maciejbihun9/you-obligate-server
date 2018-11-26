package com.maciejbihun.service;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
import com.maciejbihun.exceptions.GroupAccountOrObligationStrategyDoesNotExistsException;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.impl.BondServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
    CreatingMoneyStrategiesService creatingMoneyStrategiesService;

    private BondService bondService;

    @Before
    public void setUp(){
        this.bondService = new BondServiceImpl(bondRepository, obligationStrategyRepository, userAccountInObligationGroupRepository, creatingMoneyStrategiesService);
    }

    @Test(expected = AmountOfUnitsExceededException.class)
    public void shouldThrowExceptionIfPredictedAmountOfUnitsToPayExceedsTheLimit() throws Exception {
        // given
        Long userAccountInObligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 1001;

        User userMock = mock(User.class);
        ObligationGroup obligationGroup = new ObligationGroup(userMock, "", "", "", "");
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userMock, obligationGroup);

        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                mock(UserRegisteredService.class), obligationGroup, userAccountInObligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000);

        // when
        Mockito.when(obligationStrategyRepository.findById(obligationStrategyId)).thenReturn(Optional.of(obligationStrategy));
        Mockito.when(userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId)).thenReturn(Optional.of(userAccountInObligationGroup));
        // creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount

        // then
        bondService.createBondInObligationGroup(obligationStrategyId, amountOfUnitsToPay);
    }

    @Test(expected = GroupAccountOrObligationStrategyDoesNotExistsException.class)
    public void shouldThrowExceptionIfNoAccountOrObligation() throws Exception {
        // given
        Long userAccountInObligationGroupId = -1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 450;

        User userMock = mock(User.class);
        ObligationGroup obligationGroup = new ObligationGroup(userMock, "", "", "", "");
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userMock, obligationGroup);

        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                mock(UserRegisteredService.class), obligationGroup, userAccountInObligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000);

        // when
        Mockito.when(obligationStrategyRepository.findById(obligationStrategyId)).thenReturn(Optional.of(obligationStrategy));
        Mockito.when(userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId)).thenReturn(Optional.empty());

        // then
        bondService.createBondInObligationGroup(obligationStrategyId, amountOfUnitsToPay);
    }

    @Test
    public void shouldIncreaseGroupAccountBalanceForGivenUser() throws Exception {
        // given
        Long userAccountInObligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 100;

        User userMock = mock(User.class);
        ObligationGroup obligationGroup = new ObligationGroup(userMock, "", "", "", "");
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userMock, obligationGroup);

        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                mock(UserRegisteredService.class), obligationGroup, userAccountInObligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000);

        Bond bond = new Bond();

        // when
        Mockito.when(obligationStrategyRepository.findById(obligationStrategyId)).thenReturn(Optional.of(obligationStrategy));
        Mockito.when(userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId)).thenReturn(Optional.of(userAccountInObligationGroup));
        Mockito.when(creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(obligationStrategy.getUnitOfWorkCost(),
                                                                                                    obligationStrategy.getInterestRate(),
                                                                                                    amountOfUnitsToPay)).thenReturn(new BigDecimal("9500.00"));

        Mockito.when(bondRepository.save(bond)).thenAnswer(new Answer<Bond>() {
            @Override
            public Bond answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Bond) args[0];
            }
        });
        bondService.createBondInObligationGroup(obligationStrategyId, amountOfUnitsToPay);

        // then
        // assertEquals(Integer.valueOf(100), userAccountInObligationGroup.getBonds().get(0).getAmountOfUnitsToPay()); // bond was stored in user group account bonds list
        assertEquals(new BigDecimal("9500.00"), userAccountInObligationGroup.getAccountBalance()); // user group account balance has been increased
        assertEquals(new BigDecimal("9500.00"), obligationGroup.getAccountBalance()); // obligation group account balance has been increased
    }



}
