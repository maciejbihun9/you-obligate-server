package com.maciejbihun.repository;

import com.maciejbihun.models.User;
import org.springframework.stereotype.Repository;

public interface UserRepository {
    User saveUser(User user);
    User getUser(Long id);
}
