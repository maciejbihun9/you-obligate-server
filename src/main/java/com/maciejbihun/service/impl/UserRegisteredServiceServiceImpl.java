package com.maciejbihun.service.impl;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.repository.UserRegisteredServiceRepository;
import com.maciejbihun.service.UserRegisteredServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class UserRegisteredServiceServiceImpl implements UserRegisteredServiceService {

    @Autowired
    private UserRegisteredServiceRepository userRegisteredServiceRepository;

    /**
     * Saving UserRegisteredService system saves or updates registeredServiceTerms also.
     * If you add or delete at least one item from registeredServiceTerms then hibernate will
     * destroy entire list and will create a new list removing old ones and creating completely new list of items.
     */
    @Override
    public UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService) {
        return userRegisteredServiceRepository.save(userRegisteredService);
    }

    @Override
    public Optional<UserRegisteredService> getUserRegisteredService(Long id) {
        return userRegisteredServiceRepository.findById(id);
    }

    @Override
    public List<UserRegisteredService> getUserRegisteredServices() {
        return userRegisteredServiceRepository.findAll();
    }

    @Override
    public List<UserRegisteredService> getUserRegisteredServicesByCategory(UserRegisteredServiceCategory userRegisteredServiceCategory) {
        return userRegisteredServiceRepository.findByUserRegisteredServiceCategory(userRegisteredServiceCategory);
    }

    @Override
    public void deleteUserRegisteredService(Long id) {
        userRegisteredServiceRepository.deleteById(id);
    }

    @Override
    public boolean userRegisteredServiceExists(Long id) {
        return userRegisteredServiceRepository.existsById(id);
    }

}
