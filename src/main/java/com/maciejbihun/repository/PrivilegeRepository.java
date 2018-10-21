package com.maciejbihun.repository;

import com.maciejbihun.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BHN
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

}
