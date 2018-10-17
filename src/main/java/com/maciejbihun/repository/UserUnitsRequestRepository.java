package com.maciejbihun.repository;

import com.maciejbihun.models.UserUnitsRequest;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// use that if you do not want to implement all methods in class that implements this interface.
@NoRepositoryBean
public interface UserUnitsRequestRepository extends JpaRepository<UserUnitsRequest, Long> {

}
