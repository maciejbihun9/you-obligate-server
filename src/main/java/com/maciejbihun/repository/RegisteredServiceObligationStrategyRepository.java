package com.maciejbihun.repository;

import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredServiceObligationStrategyRepository
        extends JpaRepository<RegisteredServiceObligationStrategy, Long> {
}
