package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
import com.maciejbihun.exceptions.ObligationStrategyDoesNotExistsException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.PurchaseToken;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.RegisteredServiceObligationStrategyRepository;
import com.maciejbihun.service.BondService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Maciej Bihun
 */
@Service
@Transactional
public class BondServiceImpl implements BondService {

    private BondRepository bondRepository;

    private RegisteredServiceObligationStrategyRepository obligationStrategyRepository;

    public BondServiceImpl(BondRepository bondRepository, RegisteredServiceObligationStrategyRepository obligationStrategyRepository) {
        this.bondRepository = bondRepository;
        this.obligationStrategyRepository = obligationStrategyRepository;
    }

    @Override
    public List<Bond> getObligationGroupBonds(int obligationGroupId) {
        return bondRepository.getObligationGroupBonds(obligationGroupId);
    }

    /**
     * Creates bond in given obligation strategy.
     * This method increases account balance in all of these
     * RegisteredServiceObligationStrategy, ObligationGroup, GroupAccount, Bond accounts.
     * Hibernate will take care of cascade saving of all of those dependencies.
     * @param obligationStrategyId
     * @param amountOfUnitsToServe
     * @return
     * @throws Exception
     */
    @Override
    public Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToServe) throws Exception {
        Optional<RegisteredServiceObligationStrategy> obligationStrategyById = obligationStrategyRepository.findById(obligationStrategyId);
        if (obligationStrategyById.isPresent()){

            RegisteredServiceObligationStrategy registeredServiceObligationStrategy = obligationStrategyById.get();

            int predictedAmountOfUnitsToPay = registeredServiceObligationStrategy.getAlreadyObligatedUnitsOfWork() + amountOfUnitsToServe;

            if (predictedAmountOfUnitsToPay > registeredServiceObligationStrategy.getMaxAmountOfUnitsForObligation()){
                throw new AmountOfUnitsExceededException();
            }

            BigDecimal amountOfCreatedMoney = CreatingMoneyStrategy.amountOfCreatedMoney(registeredServiceObligationStrategy.getUnitOfWorkCost(),
                    registeredServiceObligationStrategy.getInterestRate(), amountOfUnitsToServe);

            Bond bond = new Bond();
            bond.setAmountOfCreatedMoney(amountOfCreatedMoney);
            bond.setUnitOfWorkCost(registeredServiceObligationStrategy.getUnitOfWorkCost());
            bond.setNumberOfUnitsToServe(amountOfUnitsToServe);

            // increase amount of money in the obligation group
            registeredServiceObligationStrategy.getUserAccountInObligationGroup().getObligationGroup().addMoneyToAccount(amountOfCreatedMoney);

            // create money in the group account
            registeredServiceObligationStrategy.getUserAccountInObligationGroup().addMoney(amountOfCreatedMoney);

            // create money in obligation strategy
            registeredServiceObligationStrategy.increaseCreatedMoney(amountOfCreatedMoney);

            // save bond to generate id
            bond = bondRepository.save(bond);

            // saving obligation strategy hibernate will store all associated values
            obligationStrategyRepository.save(registeredServiceObligationStrategy);
            return bond;
        } else {
            throw new ObligationStrategyDoesNotExistsException();
        }
    }

    @Override
    public PurchaseToken reserveServiceUnit() {
        return null;
    }

}
