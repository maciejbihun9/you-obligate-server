package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.ObligationGroupAccountService;
import com.maciejbihun.controller.UserService;
import com.maciejbihun.dto.ObligationGroupAccountDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.ObligationGroupAccount;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.repository.ObligationGroupAccountRepository;
import com.maciejbihun.repository.ObligationGroupRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class ObligationGroupAccountServiceImpl implements ObligationGroupAccountService {

    @Autowired
    ObligationGroupAccountRepository obligationGroupAccountRepository;

    @Autowired
    UserService userService;

    @Autowired
    EntityManager entityManager;

    /**
     * A group admin is able create an account for a user.
     */
    @Override
    public ResponseEntity<ObligationGroupAccount> createGroupAccount(ObligationGroupAccountDto obligationGroupAccountDto) {
        // ObligationGroup obligationGroup = entityManager.find(ObligationGroup.class, obligationGroupAccountDto.getObligationGroupId());
        Session session = entityManager.unwrap(Session.class);
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, obligationGroupAccountDto.getObligationGroupId());
        if (obligationGroup == null){
            MultiValueMap <String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("info", String.format("ObligationGroup with id: %s does not exist", obligationGroupAccountDto.getObligationGroupId()));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
        UserPrincipal userPrincipal = userService.loadUserByUsername(obligationGroupAccountDto.getUsername());
        ObligationGroupAccount obligationGroupAccount = new ObligationGroupAccount(userPrincipal.getUser(), obligationGroup);
        obligationGroupAccount = obligationGroupAccountRepository.save(obligationGroupAccount);
        return new ResponseEntity<>(obligationGroupAccount, HttpStatus.CREATED);
    }
}
