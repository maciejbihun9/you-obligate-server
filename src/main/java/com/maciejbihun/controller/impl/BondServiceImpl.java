package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.BondService;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.ObligationGroupAccount;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.ObligationGroupAccountRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Service
public class BondServiceImpl implements BondService {

    @Autowired
    BondRepository bondRepository;

    @Autowired
    UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Autowired
    ObligationGroupAccountRepository obligationGroupAccountRepository;

    @Override
    public ResponseEntity<Bond> save(BondDto bondDto) {
        Optional<ObligationGroupAccount> groupAccountById = obligationGroupAccountRepository.findById(bondDto.getGroupAccountId());
        Optional<UserGroupObligationStrategyForRegisteredService> obligationStrategyById = obligationStrategyRepository.findById(bondDto.getObligationStrategyId());
        if (obligationStrategyById.isPresent() && groupAccountById.isPresent()){
            Bond bond = new Bond(groupAccountById.get(), obligationStrategyById.get(), bondDto.getAmountOfUnitsToPay());
            bond = bondRepository.save(bond);
            return new ResponseEntity<Bond>(bond, HttpStatus.CREATED);
        } else {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.set("info", String.format("Obligation strategy with id: %s or group account with id: " +
                    "%s was not found", bondDto.getObligationStrategyId(), bondDto.getGroupAccountId()));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
    }
}
