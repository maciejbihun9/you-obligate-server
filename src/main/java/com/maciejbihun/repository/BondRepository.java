package com.maciejbihun.repository;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Maciej Bihun
 */
@Repository
public interface BondRepository extends JpaRepository<Bond, Long> {

    @Query("select b from Bond b where b.obligationGroupId = ?1")
    List<Bond> getObligationGroupBonds(int obligationGroupId);

}
