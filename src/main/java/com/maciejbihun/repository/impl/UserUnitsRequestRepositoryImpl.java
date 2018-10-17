package com.maciejbihun.repository.impl;

import com.maciejbihun.models.UserUnitsRequest;
import com.maciejbihun.repository.UserUnitsRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserUnitsRequestRepositoryImpl extends SimpleJpaRepository<UserUnitsRequest, Long> implements UserUnitsRequestRepository {

    @Autowired
    EntityManager entityManager;

    public UserUnitsRequestRepositoryImpl(JpaEntityInformation<UserUnitsRequest, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}
