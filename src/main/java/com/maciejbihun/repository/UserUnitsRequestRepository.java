package com.maciejbihun.repository;

import com.maciejbihun.models.UserUnitsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BHN
 */
@Repository
public interface UserUnitsRequestRepository extends JpaRepository<UserUnitsRequest, Long> {

}
