package com.maciejbihun.repository;

import com.maciejbihun.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BHN
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u where u.id IN (select user from UserAccountInObligationGroup group by user having COUNT(user) > 1)")
    List<User> getUsersWithManyGroupAccounts();

}
