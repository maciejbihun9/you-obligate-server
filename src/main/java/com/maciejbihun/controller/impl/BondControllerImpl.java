package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.BondController;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.BondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Controller
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class BondControllerImpl implements BondController {

    @Autowired
    BondService bondService;

    private BondRepository bondRepository;

    private UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    public BondControllerImpl(BondRepository bondRepository, UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository,
                              UserAccountInObligationGroupRepository userAccountInObligationGroupRepository) {
        this.bondRepository = bondRepository;
        this.obligationStrategyRepository = obligationStrategyRepository;
        this.userAccountInObligationGroupRepository = userAccountInObligationGroupRepository;
    }

    /**
     * First changes a group account balance. Adds money to the group account balance,
     * because this money is based on services that used registered.
     * Saves bond into the database.
     */
    @Override
    public ResponseEntity<Bond> createBondInObligationGroup(BondDto bondDto) {

        Optional<UserAccountInObligationGroup> groupAccountById = userAccountInObligationGroupRepository.findById(bondDto.getGroupAccountId());
        Optional<UserGroupObligationStrategyForRegisteredService> obligationStrategyById = obligationStrategyRepository.findById(bondDto.getObligationStrategyId());

        if (obligationStrategyById.isPresent() && groupAccountById.isPresent()){

            UserGroupObligationStrategyForRegisteredService userGroupObligationStrategyForRegisteredService = obligationStrategyById.get();
            UserAccountInObligationGroup userAccountInObligationGroup = groupAccountById.get();

            int predictedAmountOfUnitsToPay = userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork() + bondDto.getAmountOfUnitsToPay();

            if (bondDto.getAmountOfUnitsToPay() > userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork()){

            }

            try {
                Bond bond = bondService.createBondInObligationGroup(bondDto.getObligationStrategyId(), bondDto.getAmountOfUnitsToPay());
                // save bond to generate id
                bond = bondRepository.save(bond);

                // create money that covered by bonds
                obligationStrategyById.get().getObligationGroup().addMoneyToAccount(bond.getAmountOfCreatedMoney());

                // create money in the group account
                userAccountInObligationGroup.addMoneyToAccount(bond.getAmountOfCreatedMoney());
                userAccountInObligationGroup.getBonds().add(bond);

                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
                return new ResponseEntity<Bond>(bond, HttpStatus.CREATED);
            } catch (Exception e) {


            }

        } else {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.set("info", String.format("Obligation strategy with id: %s or group account with id: " +
                    "%s was not found", bondDto.getObligationStrategyId(), bondDto.getGroupAccountId()));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
        return null;
    }

    /**
     * Subtracts obligation units and money from bonds.
     * Subtracts money from the bond group to which it belongs.
     * This happens when other users use the services of a given user.
     */
    @Override
    public ResponseEntity<String> subtractObligationUnitFromBond(Long bondId){
        Optional<Bond> bondById = bondRepository.findById(bondId);
        if (bondById.isPresent()){
            Bond bond = bondById.get();
            try {
                bond.subtractBondUnit();
            } catch (Exception e) {
                //return new ResponseEntity<String>(HttpStatus.Er)
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
