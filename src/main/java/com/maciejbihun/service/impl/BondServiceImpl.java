package com.maciejbihun.service.impl;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import com.maciejbihun.repository.UserGroupObligationStrategyForRegisteredServiceRepository;
import com.maciejbihun.service.BondService;
import com.maciejbihun.service.CreatingMoneyStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class BondServiceImpl implements BondService {

    private static final String NOT_ACCEPTABLE_AMOUNT_OF_UNITS_PER_BOND = "Not acceptable amount of units per bond.";

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    CreatingMoneyStrategies creatingMoneyStrategies;

    @Override
    @Transactional
    public Bond createBondInObligationGroup(Long userAccountInObligationGroupId, Long obligationStrategyId, Integer amountOfUnitsToPay) {
        Optional<UserAccountInObligationGroup> groupAccountById = userAccountInObligationGroupRepository.findById(userAccountInObligationGroupId);
        Optional<UserGroupObligationStrategyForRegisteredService> obligationStrategyById = obligationStrategyRepository.findById(obligationStrategyId);

        if (obligationStrategyById.isPresent() && groupAccountById.isPresent()){

            UserGroupObligationStrategyForRegisteredService userGroupObligationStrategyForRegisteredService = obligationStrategyById.get();
            UserAccountInObligationGroup userAccountInObligationGroup = groupAccountById.get();

            int predictedAmountOfUnitsToPay = userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork() + amountOfUnitsToPay;

            if (amountOfUnitsToPay > userGroupObligationStrategyForRegisteredService.getAlreadyObligatedUnitsOfWork()){

            }

            Bond bond = createBond(userAccountInObligationGroup, userGroupObligationStrategyForRegisteredService, amountOfUnitsToPay);
            // save bond to generate id
            bond = bondRepository.save(bond);

            // create money that covered by bonds
            obligationStrategyById.get().getObligationGroup().addMoneyToAccount(bond.getAmountOfCreatedMoney());

            // create money in the group account
            userAccountInObligationGroup.addMoneyToAccount(bond.getAmountOfCreatedMoney());
            userAccountInObligationGroup.getBonds().add(bond);

            userAccountInObligationGroupRepository.save(userAccountInObligationGroup);
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
        BigDecimal amountOfCreatedMoney = creatingMoneyStrategies.computeAmountOfCreatedMoneyForBondWithDiscount(obligationStrategy.getUnitOfWorkCost(),
                obligationStrategy.getInterestRate(),
                amountOfUnitsToPay);
        return new Bond(userAccountInObligationGroup, obligationStrategy, amountOfUnitsToPay, obligationStrategy.getUnitOfWorkCost(), amountOfCreatedMoney);
    }

}
