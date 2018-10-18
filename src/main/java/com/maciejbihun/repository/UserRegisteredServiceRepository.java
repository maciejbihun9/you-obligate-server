package com.maciejbihun.repository;

import com.maciejbihun.models.UserRegisteredService;
import java.util.List;

/**
 * @author MBihun
 */
public interface UserRegisteredServiceRepository {

    UserRegisteredService saveUserRegisteredService(UserRegisteredService userRegisteredService);

    UserRegisteredService getUserRegisteredService(Long id);

    void deleteUserRegisteredService(UserRegisteredService userRegisteredService);

}
