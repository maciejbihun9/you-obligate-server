package com.maciejbihun.service;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserRegisteredService;

import java.math.BigDecimal;

public interface RegisteredServiceObligationStrategyService {

    RegisteredServiceObligationStrategy createObligationStrategy(UserRegisteredService userRegisteredService,
                                                                 UserAccountInObligationGroup userAccountInObligationGroup,
                                                                 UnitOfWork unitOfWork,
                                                                 BigDecimal unitOfWorkCost,
                                                                 BigDecimal interestRate,
                                                                 Integer debtUnitsLimit);

}
