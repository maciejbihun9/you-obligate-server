package com.maciejbihun.service.impl;

import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
import com.maciejbihun.exceptions.BondNotFoundException;
import com.maciejbihun.exceptions.ClosedBondException;
import com.maciejbihun.exceptions.GroupAccountOrObligationStrategyDoesNotExistsException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.BondService;
import com.maciejbihun.service.CreatingMoneyStrategiesService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;

@Service
public class BondServiceImpl implements BondService {

    private static final String NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND = "Not acceptable amount of units per bond.";

    private BondRepository bondRepository;

    private UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    private CreatingMoneyStrategiesService creatingMoneyStrategiesService;

    public BondServiceImpl(BondRepository bondRepository, UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository,
                           UserAccountInObligationGroupRepository userAccountInObligationGroupRepository, CreatingMoneyStrategiesService creatingMoneyStrategiesService) {
        this.bondRepository = bondRepository;
        this.obligationStrategyRepository = obligationStrategyRepository;
        this.userAccountInObligationGroupRepository = userAccountInObligationGroupRepository;
        this.creatingMoneyStrategiesService = creatingMoneyStrategiesService;
    }

    @Override
    @Transactional
    public Bond createBondInObligationGroup(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception {
        Optional<UserGroupObligationStrategyForRegisteredService> obligationStrategyById = obligationStrategyRepository.findById(obligationStrategyId);
        if (obligationStrategyById.isPresent()){

            UserGroupObligationStrategyForRegisteredService userGroupObligationStrategyForRegisteredService = obligationStrategyById.get();

            int predictedAmountOfUnitsToPay = userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork() + amountOfUnitsToPay;

            if (predictedAmountOfUnitsToPay > userGroupObligationStrategyForRegisteredService.getMaxAmountOfUnitsForObligation()){
                throw new AmountOfUnitsExceededException();
            }

            Bond bond = new Bond(userGroupObligationStrategyForRegisteredService, amountOfUnitsToPay);

            // increase amount of money in the obligation group
            userGroupObligationStrategyForRegisteredService.getObligationGroup().addMoneyToAccount(bond.getAmountOfCreatedMoney());

            // create money in the group account
            userGroupObligationStrategyForRegisteredService.getUserAccountInObligationGroup().addMoneyToAccount(bond.getAmountOfCreatedMoney());

            // save bond to generate id
            bond = bondRepository.save(bond);

            obligationStrategyRepository.save(userGroupObligationStrategyForRegisteredService);
            // userAccountInObligationGroupRepository.save(userGroupObligationStrategyForRegisteredService.getUserAccountInObligationGroup());

            return bond;
        } else {
            throw new GroupAccountOrObligationStrategyDoesNotExistsException();
        }
    }

    /**
     * Bond has to be created with minimum amount of units to pay that is given in obligation strategy for given registered service.
     * Bond can be created with different creating money strategies.
     */
    /*@Override
    public Bond createBond(UserGroupObligationStrategyForRegisteredService obligationStrategy, Integer amountOfUnitsToPay) {
        return new Bond(obligationStrategy, amountOfUnitsToPay);
    }*/


}
