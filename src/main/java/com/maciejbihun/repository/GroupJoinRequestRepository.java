package com.maciejbihun.repository;

import com.maciejbihun.models.GroupJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maciej Bihun
 */
@Repository
public interface GroupJoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {
}
