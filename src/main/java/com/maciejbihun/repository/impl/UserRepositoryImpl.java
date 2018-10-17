package com.maciejbihun.repository.impl;

import com.maciejbihun.models.User;
import com.maciejbihun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public User saveUser(User user) {
        entityManager.persist(user);
        // return the same user with generated ID
        return user;
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }
}
