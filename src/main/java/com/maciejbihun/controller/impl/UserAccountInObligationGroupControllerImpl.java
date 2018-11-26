package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserAccountInObligationGroupController;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.dto.ObligationGroupAccountDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;

/**
 * @author Maciej Bihun
 */
@Controller
@Transactional
public class UserAccountInObligationGroupControllerImpl implements UserAccountInObligationGroupController {

    @Autowired
    UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager entityManager;

    /**
     * A group admin is able create an account for a user.
     */
    @Override
    public ResponseEntity<UserAccountInObligationGroup> createGroupAccount(ObligationGroupAccountDto obligationGroupAccountDto) {
        // ObligationGroup obligationGroup = entityManager.find(ObligationGroup.class, obligationGroupAccountDto.getObligationGroupId());
        Session session = entityManager.unwrap(Session.class);
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, obligationGroupAccountDto.getObligationGroupId());
        if (obligationGroup == null){
            MultiValueMap <String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("info", String.format("ObligationGroup with id: %s does not exist", obligationGroupAccountDto.getObligationGroupId()));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
        UserPrincipal userPrincipal = userService.loadUserByUsername(obligationGroupAccountDto.getUsername());
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userPrincipal.getUser(), obligationGroup);
        userAccountInObligationGroup = userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
        return new ResponseEntity<>(userAccountInObligationGroup, HttpStatus.CREATED);
    }

    /**
     * Returns UserAccountInObligationGroup with eagerly initialized bonds list.
     * @param userAccountInObligationGroupId
     * @return UserAccountInObligationGroup entity with initialized bonds
     */
    @Override
    public ResponseEntity<UserAccountInObligationGroup> getUserAccountInObligationGroupWithBonds(Long userAccountInObligationGroupId) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.accountBonds");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        UserAccountInObligationGroup userAccountInObligationGroup = entityManager.find(UserAccountInObligationGroup.class, userAccountInObligationGroupId, properties);
        if (userAccountInObligationGroup == null){
            MultiValueMap <String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("info", String.format("UserAccountInObligationGroup with id: %s does not exist", userAccountInObligationGroupId));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userAccountInObligationGroup, HttpStatus.OK);
    }
}
