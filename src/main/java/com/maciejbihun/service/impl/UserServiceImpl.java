package com.maciejbihun.service.impl;

import com.maciejbihun.controller.UserController;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.repository.UserRepository;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users registered in the system.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Register new user in the system.
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Update user data in the system.
     */
    @Override
    public User saveUserData(User user){
        return userRepository.save(user);
    }

    /**
     * Loads user by it's username.
     */
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

}
