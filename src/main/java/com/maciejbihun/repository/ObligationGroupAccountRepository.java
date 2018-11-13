package com.maciejbihun.repository;

import com.maciejbihun.models.ObligationGroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Maciej Bihun
 */
@Repository
public interface ObligationGroupAccountRepository extends JpaRepository<ObligationGroupAccount, Long> {

}
