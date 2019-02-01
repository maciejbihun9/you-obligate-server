package com.maciejbihun.repository;

import com.maciejbihun.models.GroupJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Maciej Bihun
 */
@Repository
public interface GroupJoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {

    @Query("select g from GroupJoinRequest g" +
            " join fetch g.obligationGroup o" +
            " where o.id = ?1")
    List<GroupJoinRequest> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId);

}
