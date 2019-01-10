package com.maciejbihun.repository;

import com.maciejbihun.models.UserAccountInObligationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
@Repository
public interface UserAccountInObligationGroupRepository extends JpaRepository<UserAccountInObligationGroup, Long> {

    @Query("select u.accountBalance " +
            "from UserAccountInObligationGroup u " +
            "where u.user.id = :userId and u.obligationGroup.id = :obligationGroupId")
    BigDecimal getUserAccountBalanceInGivenObligationGroup(Long userId, Long obligationGroupId);

}
