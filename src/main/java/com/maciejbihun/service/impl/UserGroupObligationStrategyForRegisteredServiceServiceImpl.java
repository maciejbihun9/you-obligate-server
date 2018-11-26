package com.maciejbihun.service.impl;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.UserGroupObligationStrategyForRegisteredServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class UserGroupObligationStrategyForRegisteredServiceServiceImpl implements
        UserGroupObligationStrategyForRegisteredServiceService {

    @Autowired
    UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Override
    public UserGroupObligationStrategyForRegisteredService createObligationStrategy(ObligationGroup obligationGroup,
                                                                                    UserRegisteredService userRegisteredService,
                                                                                    UnitOfWork unitOfWork,
                                                                                    BigDecimal unitOfWorkCost,
                                                                                    BigDecimal interestRate,
                                                                                    Integer debtUnitsLimit) {
        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                userRegisteredService,
                obligationGroup,
                unitOfWork,
                unitOfWorkCost,
                interestRate,
                debtUnitsLimit
        );
        return obligationStrategyRepository.save(obligationStrategy);
    }

}
