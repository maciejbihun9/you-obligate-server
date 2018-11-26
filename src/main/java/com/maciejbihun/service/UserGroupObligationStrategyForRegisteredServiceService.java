package com.maciejbihun.service;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.UserGroupObligationStrategyForRegisteredServiceDto;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.models.UserRegisteredService;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserGroupObligationStrategyForRegisteredServiceService {

    UserGroupObligationStrategyForRegisteredService createObligationStrategy(ObligationGroup obligationGroup,
                                                                             UserRegisteredService userRegisteredService,
                                                                             UnitOfWork unitOfWork,
                                                                             BigDecimal unitOfWorkCost,
                                                                             BigDecimal interestRate,
                                                                             Integer debtUnitsLimit);

}
