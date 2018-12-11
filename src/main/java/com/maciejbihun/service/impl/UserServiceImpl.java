package com.maciejbihun.service.impl;

import com.maciejbihun.models.*;
import com.maciejbihun.repository.UserRepository;
import com.maciejbihun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * Get all users registered in the system.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Creates or updates a user in the system.
     */
    @Override
    public User saveUser(User user) {
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

    @Override
    public User loadUserWithRegisteredServices(String username) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.userRegisteredServices");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        return entityManager.find(User.class, loadUserByUsername(username).getUser().getId(), properties);
    }

    @Override
    public List<User> getAllUsersWithRegisteredServices() {
        return userRepository.getAllUsersWithRegisteredServices();
    }

    @Override
    public List<User> getRecommendedUsersToJoinGroup(Set<ServiceTag> groupRegisteredServicesTags, List<User> usersWithRegisteredServices) {

        // use the copy constructor
        Map<User, Integer> numberOfCommonServiceTags = new TreeMap<>();
        usersWithRegisteredServices.forEach(userWithRegisteredServices -> {
            Set<ServiceTag> commonServicesTags = new HashSet<>(groupRegisteredServicesTags);
            List<ServiceTag> userRegisteredServicesTags = userWithRegisteredServices.getUserRegisteredServices().stream()
                    .flatMap(userRegisteredService -> userRegisteredService.getUserRegisteredServiceTags().stream()).collect(Collectors.toList());
            // common part of service tags
            commonServicesTags.retainAll(userRegisteredServicesTags);
            numberOfCommonServiceTags.put(userWithRegisteredServices, commonServicesTags.size());
        });

        List<Map.Entry<User, Integer>> list = new ArrayList<>(numberOfCommonServiceTags.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        List<User> recommendedUsers = new ArrayList<>();
        for (Map.Entry<User, Integer> entry : list) {
            recommendedUsers.add(entry.getKey());
        }
        return recommendedUsers;
    }

}
