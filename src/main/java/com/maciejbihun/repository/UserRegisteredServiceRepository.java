package com.maciejbihun.repository;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BHN
 */
@Repository
public interface UserRegisteredServiceRepository extends JpaRepository<UserRegisteredService, Long> {

    List<UserRegisteredService> findByUserRegisteredServiceCategory(UserRegisteredServiceCategory userRegisteredServiceCategory);

}
