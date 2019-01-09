package com.maciejbihun.service;

import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.ServiceTag;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

/**
 * @author Maciej Bihun
 */
public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    UserPrincipal loadUserByUsername(String username);
    // User getLoggedInUser();

    /**
     * Returns a user with eagerly fetched list of registered services.
     */
    User loadUserWithRegisteredServices(String username);

    /**
     * Returns all users with registered services.
     */
    List<User> getAllUsersWithRegisteredServices();

    /**
     * Returns a list of users recommended for a group to join using theirs registered services tags.
     */
    List<User> getRecommendedUsersToJoinGroup(Set<ServiceTag> groupRegisteredServicesTags, List<User> usersWithRegisteredServices);
}
