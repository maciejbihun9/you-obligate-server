package com.maciejbihun.repository;

import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.models.UserRegisteredServiceCategory;
import com.maciejbihun.models.UserUnitsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author MBihun
 */
@NoRepositoryBean
public interface UserRegisteredServiceRepository extends JpaRepository<UserRegisteredService, Long> {

    List<UserRegisteredService> findByStatus(UserRegisteredServiceCategory userRegisteredServiceCategory);

}
