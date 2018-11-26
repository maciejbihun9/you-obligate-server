package com.maciejbihun.service;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.models.Bond;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
@ActiveProfiles("test")
@Transactional
public class BondServiceIntegrationTest {

    @Autowired
    private BondService bondService;

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccountInObligationGroupService userAccountInObligationGroupService;

    // createBondInObligationGroup() --- TEST METHODS --- //

    @Test
    public void shouldIncreaseAmountOfMoneyInObligationGroupAccount() throws Exception {
        // given
        Long obligationGroupId = 1L;
        Long obligationStrategyId = 1L;
        Integer amountOfUnitsToPay = 100;
        BigDecimal groupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        bondService.createBondInObligationGroup(obligationStrategyId, amountOfUnitsToPay);

        // then
        Optional<ObligationGroup> obligationGroupById = obligationGroupRepository.findById(obligationGroupId);
        assertEquals(groupAccountBalance, obligationGroupById.get().getAccountBalance());
    }

    @Test
    public void shouldIncreaseAmountOfMoneyInUserGroupAccount() throws Exception {
        // given
        Long obligationStrategyId = 2L;
        Integer amountOfUnitsToPay = 100;
        BigDecimal groupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        Bond createdBond = bondService.createBondInObligationGroup(obligationStrategyId, amountOfUnitsToPay);

        // then
        assertEquals(groupAccountBalance, createdBond.getUserAccountInObligationGroup().getAccountBalance());
    }

    // test what happens when user creates many bonds in the same group
    @Test
    public void shouldIncreaseAmountOfMoneyInTheSameUserGroupAccountForManyCreatedBonds() throws Exception {
        // given
        Long obligationStrategyId = 3L;
        BigDecimal expectedGroupAccountBalance = BigDecimal.valueOf(3800000, 2);

        // when
        bondService.createBondInObligationGroup(obligationStrategyId, 100);
        bondService.createBondInObligationGroup(obligationStrategyId, 100);
        bondService.createBondInObligationGroup(obligationStrategyId, 100);
        Bond createdBond = bondService.createBondInObligationGroup(obligationStrategyId, 100);

        Optional<UserAccountInObligationGroup> userAccountInObligationGroupById =
                userAccountInObligationGroupRepository.findById(createdBond.getUserAccountInObligationGroup().getId());
        UserAccountInObligationGroup userAccountInObligationGroup = userAccountInObligationGroupById.get();

        // then
        assertEquals(expectedGroupAccountBalance, userAccountInObligationGroup.getAccountBalance());
    }

    // test what happens when a user create bonds in many obligation groups
    @Test
    public void shouldIncreaseAccountBalanceInBothAccountsSeparately() throws Exception {
        // given
        List<User> usersWithManyGroupAccounts = userRepository.getUsersWithManyGroupAccounts();
        User userWithManyGroupAccounts = usersWithManyGroupAccounts.get(0);
        List<UserAccountInObligationGroup> userAccountsInObligationGroups = userWithManyGroupAccounts.getUserAccountInObligationGroups();
        BigDecimal expectedGroupAccountBalance = BigDecimal.valueOf(950000, 2);

        // when
        bondService.createBondInObligationGroup(userAccountInObligationGroupService.getUserAccountWithObligationStrategies(1L)
                .getUserObligationStrategies().get(0).getId(), 100);
        bondService.createBondInObligationGroup(userAccountInObligationGroupService.getUserAccountWithObligationStrategies(2L)
                .getUserObligationStrategies().get(0).getId(), 100);

        // then
        assertEquals(expectedGroupAccountBalance, userAccountsInObligationGroups.get(0).getAccountBalance());
        assertEquals(expectedGroupAccountBalance, userAccountsInObligationGroups.get(1).getAccountBalance());

        //given
        bondService.createBondInObligationGroup(userAccountInObligationGroupService.getUserAccountWithObligationStrategies(1L).getUserObligationStrategies().get(0).getId(), 100);
        expectedGroupAccountBalance = BigDecimal.valueOf(1900000, 2);
        assertEquals(expectedGroupAccountBalance, userAccountsInObligationGroups.get(1).getAccountBalance());
    }

    // createBond() --- TEST METHODS --- //
}
