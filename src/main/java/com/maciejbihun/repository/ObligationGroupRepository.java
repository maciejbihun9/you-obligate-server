package com.maciejbihun.repository;

import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObligationGroupRepository extends JpaRepository<ObligationGroup, Long> {

    @Query("select o " +
            "from ObligationGroup o " +
            "join fetch o.userAccountsInObligationGroup u " +
            "join fetch u.userObligationStrategies uo " +
            "join fetch uo.userRegisteredService ur " +
            "join fetch ur.userRegisteredServiceTags urt ")
    List<ObligationGroup> getObligationGroupsWithRegisteredServicesTags();

}

