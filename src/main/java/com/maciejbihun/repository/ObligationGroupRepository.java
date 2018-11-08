package com.maciejbihun.repository;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObligationGroupRepository extends JpaRepository<ObligationGroup, Long> {


}

