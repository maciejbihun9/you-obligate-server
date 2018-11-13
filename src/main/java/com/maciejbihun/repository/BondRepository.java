package com.maciejbihun.repository;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author BHN
 */
@Repository
public interface BondRepository extends JpaRepository<Bond, Long> {

}
