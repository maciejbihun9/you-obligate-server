package com.maciejbihun.controller;

import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;

public interface UserGroupObligationStrategyForRegisteredServiceController {

    UserGroupObligationStrategyForRegisteredService createObligationStrategy(UserGroupObligationStrategyForRegisteredServiceDto userGroupObligationStrategyForRegisteredServiceDto);

}
