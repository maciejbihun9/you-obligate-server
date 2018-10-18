package com.maciejbihun.repository;

import com.maciejbihun.models.User;
import com.maciejbihun.models.UserRegisteredService;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository {
    User saveUser(User user);
    User getUser(Long id);
    List<UserRegisteredService> getUserRegisteredServices(Long id);
}
