package com.maciejbihun.service.impl;

import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.repository.RegisteredServiceObligationStrategyRepository;
import com.maciejbihun.service.RegisteredServiceObligationStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RegisteredServiceObligationStrategyServiceImpl implements
        RegisteredServiceObligationStrategyService {

    @Autowired
    RegisteredServiceObligationStrategyRepository obligationStrategyRepository;

    @Override
    public RegisteredServiceObligationStrategy saveObligationStrategy(RegisteredServiceObligationStrategy registeredServiceObligationStrategy){
        return obligationStrategyRepository.save(registeredServiceObligationStrategy);
    }

}
