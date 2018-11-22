package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
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
import java.util.Optional;

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
    public Bond createBondInObligationGroup(Long userAccountInObligationGroupId, Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception {
        Optional<UserAccountInObligationGroup> groupAccountById = userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId);
        Optional<UserGroupObligationStrategyForRegisteredService> obligationStrategyById = obligationStrategyRepository.findById(obligationStrategyId);

        if (obligationStrategyById.isPresent() && groupAccountById.isPresent()){

            UserGroupObligationStrategyForRegisteredService userGroupObligationStrategyForRegisteredService = obligationStrategyById.get();
            UserAccountInObligationGroup userAccountInObligationGroup = groupAccountById.get();

            int predictedAmountOfUnitsToPay = userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork() + amountOfUnitsToPay;

            if (predictedAmountOfUnitsToPay > userGroupObligationStrategyForRegisteredService.getMaxAmountOfUnitsForObligation()){
                throw new AmountOfUnitsExceededException();
            }

            Bond bond = createBond(userAccountInObligationGroup, userGroupObligationStrategyForRegisteredService, amountOfUnitsToPay);

            // create money that covered by bonds
            obligationStrategyById.get().getObligationGroup().addMoneyToAccount(bond.getAmountOfCreatedMoney());

            // create money in the group account
            userAccountInObligationGroup.addMoneyToAccount(bond.getAmountOfCreatedMoney());

            // save bond to generate id
            bondRepository.save(bond);

            userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
        } else {
            throw new GroupAccountOrObligationStrategyDoesNotExistsException();
        }
        return null;
    }

    /**
     * Bond has to be created with minimum amount of units to pay that is given in obligation strategy for given registered service.
     * Bond can be created with different creating money strategies.
     */
    @Override
    public Bond createBond(UserAccountInObligationGroup userAccountInObligationGroup, UserGroupObligationStrategyForRegisteredService obligationStrategy, Integer amountOfUnitsToPay) {
        if (amountOfUnitsToPay < obligationStrategy.getMinAmountOfUnitsPerBond()){
            throw new IllegalArgumentException(String.format(NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND + " It was %s, but it should be %s",
                    amountOfUnitsToPay, obligationStrategy.getMinAmountOfUnitsPerBond()));
        }
        BigDecimal amountOfCreatedMoney = creatingMoneyStrategiesService.computeAmountOfCreatedMoneyForBondWithDiscount(obligationStrategy.getUnitOfWorkCost(),
                obligationStrategy.getInterestRate(),
                amountOfUnitsToPay);
        Bond bond = new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay, obligationStrategy.getUnitOfWorkCost(), amountOfCreatedMoney);
        return bond;
    }

}
