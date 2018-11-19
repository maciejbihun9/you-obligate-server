package com.maciejbihun.repository;

import com.maciejbihun.models.UserAccountInObligationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maciej Bihun
 */
@Repository
public interface UserAccountInObligationGroupRepository extends JpaRepository<UserAccountInObligationGroup, Long> {

}
