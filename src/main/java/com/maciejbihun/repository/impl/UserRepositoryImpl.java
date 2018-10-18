package com.maciejbihun.repository.impl;

import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserUnitsRequest;
import com.maciejbihun.repository.UserRepository;
import com.maciejbihun.repository.UserUnitsRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepository {

    @Autowired
    EntityManager entityManager;

    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

}
