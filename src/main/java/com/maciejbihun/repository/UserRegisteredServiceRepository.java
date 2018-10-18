package com.maciejbihun.repository;

import com.maciejbihun.models.UserRegisteredService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author MBihun
 */
@NoRepositoryBean
public interface UserRegisteredServiceRepository extends JpaRepository<UserRegisteredService, Long> {

}
