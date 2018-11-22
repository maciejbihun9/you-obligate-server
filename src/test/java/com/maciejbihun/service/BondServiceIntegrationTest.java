package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.repository.ObligationGroupRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
public class BondServiceIntegrationTest {

    @Autowired
    private BondService bondService;

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    private UserRepository userRepository;

    // createBondInObligationGroup() --- TEST METHODS --- //

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfUserGroupAccountDoesNotExists() throws Exception {
        Long userAccountInObligationGroupId = -1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 1001;
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, amountOfUnitsToPay);
    }

    @Test
    public void shouldIncreaseAmountOfMoneyInObligationGroupAccount() throws Exception {
        // given
        Long userAccountInObligationGroupId = 1L;
        Long obligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 100;
        BigDecimal groupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, amountOfUnitsToPay);

        // then
        Optional<ObligationGroup> obligationGroupById = obligationGroupRepository.findById(obligationGroupId);
        assertEquals(groupAccountBalance, obligationGroupById.get().getAccountBalance());
    }

    @Test
    public void shouldIncreaseAmountOfMoneyInUserGroupAccount() throws Exception {
        // given
        Long userAccountInObligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 100;
        BigDecimal groupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, amountOfUnitsToPay);
        Optional<UserAccountInObligationGroup> userAccountInObligationGroupById = userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId);
        UserAccountInObligationGroup userAccountInObligationGroup = userAccountInObligationGroupById.get();

        // then
        assertEquals(groupAccountBalance, userAccountInObligationGroup.getAccountBalance());
    }

    // test what happens when user creates many bonds in the same group
    @Test
    public void shouldIncreaseAmountOfMoneyInTheSameUserGroupAccountForManyCreatedBonds() throws Exception {
        // given
        Long userAccountInObligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        BigDecimal expectedGroupAccountBalance = BigDecimal.valueOf(5700000, 2);

        // when
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, 100);
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, 150);
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, 250);
        bondService.createBondInObligationGroup(userAccountInObligationGroupId, obligationStrategyId, 100);

        Optional<UserAccountInObligationGroup> userAccountInObligationGroupById = userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId);
        UserAccountInObligationGroup userAccountInObligationGroup = userAccountInObligationGroupById.get();

        // then
        assertEquals(expectedGroupAccountBalance, userAccountInObligationGroup.getAccountBalance());
    }

    // test what happens when a user create bonds in many obligation groups
    @Test
    public void shouldIncreaseAccountBalanceInBothAccountsSeparately() throws Exception {
        // given
        List<User> usersWithManyGroupAccounts = userRepository.getUsersWithManyGroupAccounts();
        User userWithmanygroupAccounts = usersWithManyGroupAccounts.get(0);
        List<UserAccountInObligationGroup> userAccountInObligationGroups = userWithmanygroupAccounts.getUserAccountInObligationGroups();
        BigDecimal expectedGroupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        bondService.createBondInObligationGroup(userAccountInObligationGroups.get(0).getId(),
                userAccountInObligationGroups.get(0).getUserObligationStrategies().get(0).getId(), 100);

        bondService.createBondInObligationGroup(userAccountInObligationGroups.get(1).getId(),
                userAccountInObligationGroups.get(1).getUserObligationStrategies().get(0).getId(), 100);

        // then
        assertEquals(expectedGroupAccountBalance, userAccountInObligationGroups.get(0).getAccountBalance());
        assertEquals(expectedGroupAccountBalance, userAccountInObligationGroups.get(1).getAccountBalance());

        //given
        bondService.createBondInObligationGroup(userAccountInObligationGroups.get(1).getId(),
                userAccountInObligationGroups.get(1).getUserObligationStrategies().get(0).getId(), 100);
        expectedGroupAccountBalance = BigDecimal.valueOf(1900000, 2);
        assertEquals(expectedGroupAccountBalance, userAccountInObligationGroups.get(1).getAccountBalance());


    }

    // test if other group accounts are not affected after creating a bond in one group

    // createBond() --- TEST METHODS --- //
}
