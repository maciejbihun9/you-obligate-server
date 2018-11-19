package com.maciejbihun.controller;

import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import org.springframework.http.ResponseEntity;

public interface UserGroupObligationStrategyForRegisteredServiceController {

    ResponseEntity<UserGroupObligationStrategyForRegisteredService> createObligationStrategy(UserGroupObligationStrategyForRegisteredServiceDto userGroupObligationStrategyForRegisteredServiceDto);

}
