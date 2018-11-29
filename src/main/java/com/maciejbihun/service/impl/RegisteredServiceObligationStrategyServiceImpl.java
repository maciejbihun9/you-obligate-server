package com.maciejbihun.service.impl;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.repository.RegisteredServiceObligationStrategyRepository;
import com.maciejbihun.service.RegisteredServiceObligationStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class RegisteredServiceObligationStrategyServiceImpl implements
        RegisteredServiceObligationStrategyService {

    @Autowired
    RegisteredServiceObligationStrategyRepository obligationStrategyRepository;

    @Override
    public RegisteredServiceObligationStrategy createObligationStrategy(UserRegisteredService userRegisteredService,
                                                                        UserAccountInObligationGroup userAccountInObligationGroup,
                                                                        UnitOfWork unitOfWork,
                                                                        BigDecimal unitOfWorkCost,
                                                                        BigDecimal interestRate,
                                                                        Integer debtUnitsLimit) {
        RegisteredServiceObligationStrategy obligationStrategy = new RegisteredServiceObligationStrategy(
                userRegisteredService,
                userAccountInObligationGroup,
                unitOfWork,
                unitOfWorkCost,
                interestRate,
                2,
                debtUnitsLimit
        );
        return obligationStrategyRepository.save(obligationStrategy);
    }

}
