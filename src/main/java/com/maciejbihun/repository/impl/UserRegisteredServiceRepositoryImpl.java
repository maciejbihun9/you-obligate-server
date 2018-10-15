package com.maciejbihun.repository.impl;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRegisteredServiceRepositoryImpl implements UserRegisteredServiceRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService) {
        entityManager.persist(userRegisteredService);
        return userRegisteredService;
    }

    @Override
    public UserRegisteredService getUserRegisteredService(Long id) {
        return entityManager.find(UserRegisteredService.class, id);
    }

    @Override
    public List<UserRegisteredService> getAllUserRegisteredServices() {
        TypedQuery<UserRegisteredService> allUserRegisteredServices = entityManager.createQuery("SELECT u FROM UserRegisteredService u", UserRegisteredService.class);
        return allUserRegisteredServices.getResultList();
    }

    @Override
    public void deleteUserRegisteredService(UserRegisteredService userRegisteredService) {
        entityManager.remove(userRegisteredService);
    }
}
