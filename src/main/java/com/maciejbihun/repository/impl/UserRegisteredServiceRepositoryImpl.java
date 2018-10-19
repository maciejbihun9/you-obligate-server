package com.maciejbihun.repository.impl;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRegisteredServiceRepositoryImpl extends SimpleJpaRepository<UserRegisteredService, Long> implements UserRegisteredServiceRepository {

    @Autowired
    EntityManager entityManager;

    public UserRegisteredServiceRepositoryImpl(EntityManager em) {
        super(UserRegisteredService.class, em);
    }

    @Override
    public List<UserRegisteredService> findByStatus(String status) {
        return null;
    }
}
