package com.maciejbihun.service.impl;

import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.service.UserAccountInObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.HashMap;

/**
 * @author Maciej Bihun
 */
@Service
public class UserAccountInObligationGroupServiceImpl implements UserAccountInObligationGroupService {

    @Autowired
    private EntityManager entityManager;

    /**
     * Returns UserAccountInObligationGroup entity with initialized userObligationStrategies.
     */
    @Override
    public UserAccountInObligationGroup getUserAccountWithObligationStrategies(Long userAccountInObligationGroupId) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.userObligationStrategies");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(UserAccountInObligationGroup.class, userAccountInObligationGroupId, properties);
    }

}
