package com.maciejbihun.service.impl;

import com.maciejbihun.datatype.BondStatus;
import com.maciejbihun.exceptions.AmountOfUnitsExceededException;
import com.maciejbihun.exceptions.BondDoesNotExistsException;
import com.maciejbihun.exceptions.ClosedBondException;
import com.maciejbihun.exceptions.ObligationStrategyDoesNotExistsException;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.RegisteredServiceObligationStrategy;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.RegisteredServiceObligationStrategyRepository;
import com.maciejbihun.service.BondService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /**
     * Creates bond in given obligation strategy.
     * This method increases account balance in all of these
     * RegisteredServiceObligationStrategy, ObligationGroup, GroupAccount, Bond accounts.
     * Hibernate will take care of cascade saving of all of those dependencies.
     * @param obligationStrategyId
     * @param amountOfUnitsToPay
     * @return
     * @throws Exception
     */
    @Override
    public Bond createBondInObligationStrategy(Long obligationStrategyId, Integer amountOfUnitsToPay) throws Exception {
        Optional<RegisteredServiceObligationStrategy> obligationStrategyById = obligationStrategyRepository.findById(obligationStrategyId);
        if (obligationStrategyById.isPresent()){

            RegisteredServiceObligationStrategy registeredServiceObligationStrategy = obligationStrategyById.get();

            int predictedAmountOfUnitsToPay = registeredServiceObligationStrategy.getAlreadyObligatedUnitsOfWork() + amountOfUnitsToPay;

            if (predictedAmountOfUnitsToPay > registeredServiceObligationStrategy.getMaxAmountOfUnitsForObligation()){
                throw new AmountOfUnitsExceededException();
            }

            Bond bond = new Bond(registeredServiceObligationStrategy, amountOfUnitsToPay);

            // increase amount of money in the obligation group
            registeredServiceObligationStrategy.getUserAccountInObligationGroup().getObligationGroup().addMoneyToAccount(bond.getAvailableBalance());

            // create money in the group account
            registeredServiceObligationStrategy.getUserAccountInObligationGroup().addMoneyToAccount(bond.getAvailableBalance());

            // create money in obligation strategy
            registeredServiceObligationStrategy.increaseCreatedMoney(bond.getAvailableBalance());

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
    public Integer subtractObligationUnitFromBond(Long bondId) {
        Optional<Bond> bondById = bondRepository.findById(bondId);
        if (!bondById.isPresent()){
            throw new BondDoesNotExistsException();
        }
        Bond bond = bondById.get();
        if (bond.getBondStatus().equals(BondStatus.CLOSED)){
            throw new ClosedBondException();
        }
        bondRepository.save(bond);
        return bond.subtractBondUnit();
    }

}
