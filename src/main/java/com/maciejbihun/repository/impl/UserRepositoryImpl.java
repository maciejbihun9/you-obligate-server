package com.maciejbihun.repository.impl;

import com.maciejbihun.models.User;
import com.maciejbihun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepository {

    @Autowired
    EntityManager entityManager;

    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

}
