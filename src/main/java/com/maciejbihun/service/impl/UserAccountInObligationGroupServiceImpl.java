package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.service.ObligationGroupService;
import com.maciejbihun.service.UserAccountInObligationGroupService;
import com.maciejbihun.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class UserAccountInObligationGroupServiceImpl implements UserAccountInObligationGroupService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    private ObligationGroupService obligationGroupService;

    @Autowired
    private UserService userService;

    @Override
    public UserAccountInObligationGroup saveUserAccountInObligationGroup(UserAccountInObligationGroup userAccountInObligationGroup) {
        return userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
    }

    /**
     * Returns UserAccountInObligationGroup entity with initialized userObligationStrategies.
     */
    @Override
    public UserAccountInObligationGroup getUserAccountInObligationGroupWithObligationStrategies(Long userAccountInObligationGroupId) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.userObligationStrategies");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(UserAccountInObligationGroup.class, userAccountInObligationGroupId, properties);
    }

    /**
     * Returns UserAccountInObligationGroup by id or null if it was not found.
     */
    @Override
    public UserAccountInObligationGroup getUserAccountInObligationGroup(Long accountId) {
        return userAccountInObligationGroupRepository.findById(accountId).orElse(null);
    }

    /**
     * Creates an account for a user in given obligation group.
     */
    @Override
    public UserAccountInObligationGroup createUserAccountInObligationGroup(String username, Long obligationGroupId) throws ObligationGroupDoesNotExistsException {
        Session session = entityManager.unwrap(Session.class);
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, obligationGroupId);
        if (obligationGroup == null){
            throw new ObligationGroupDoesNotExistsException(obligationGroupId);
        }
        UserPrincipal userPrincipal = userService.loadUserByUsername(username);
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup();
        userAccountInObligationGroup.setUser(userPrincipal.getUser());
        userAccountInObligationGroup.setObligationGroup(obligationGroup);
        return userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
    }

    /**
     * Returns UserAccountInObligationGroup for given user and given obligation group.
     */
    @Override
    public UserAccountInObligationGroup getUserAccountInObligationGroupForObligationGroupAndUser(Long userId, Long obligationGroupId) {
        return userAccountInObligationGroupRepository.getUserAccountInObligationGroupForObligationGroupAndUser(userId, obligationGroupId);
    }

    @Override
    public BigDecimal getUserAccountBalanceInGivenObligationGroup(Long userId, Long obligationGroupId) {
        return userAccountInObligationGroupRepository.getUserAccountBalanceInGivenObligationGroup(userId, obligationGroupId);
    }

    @Override
    public boolean userHasEnoughMoney(User user) {
        return true;
    }

}
