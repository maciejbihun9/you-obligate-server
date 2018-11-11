package com.maciejbihun.repository;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupObligationStrategyForRegisteredServiceRepository
        extends JpaRepository<UserGroupObligationStrategyForRegisteredService, Long> {
}
