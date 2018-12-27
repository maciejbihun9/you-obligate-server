package com.maciejbihun.model;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.*;
import com.maciejbihun.service.impl.CreatingMoneyStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Maciej Bihun
 */
@RunWith(MockitoJUnitRunner.class)
public class BondTest {

    @Test
    public void shouldReturnCorrectAmountOfLeftUnits(){
        // given
        int amountOfUnitsToServe = 100;
        BigDecimal unitOfWorkCost = BigDecimal.valueOf(10000, 2);
        Bond bond = new Bond();
        bond.setNumberOfUnitsToServe(amountOfUnitsToServe);
        bond.setUnitOfWorkCost(unitOfWorkCost);
        bond.setAmountOfCreatedMoney(CreatingMoneyStrategy.amountOfCreatedMoney(unitOfWorkCost,
                BigDecimal.valueOf(0, 2), amountOfUnitsToServe));
        // when
        bond.subtractUnits(67);
        // then
        assertEquals(Integer.valueOf(33), bond.getNumberOfUnitsToServe());
    }

    /*@Test
    public void reservingUnitsShouldWorkWellInMultithreadedEnvironment() throws InterruptedException {
        // given
        RegisteredServiceObligationStrategy registeredServiceObligationStrategy = new RegisteredServiceObligationStrategy(
                Mockito.mock(UserRegisteredService.class),
                Mockito.mock(UserAccountInObligationGroup.class),
                UnitOfWork.SERVICE,
                BigDecimal.valueOf(10000, 2),
                BigDecimal.valueOf(0, 2),
                2,
                1000
        );
        Integer amountOfUnitsToPay = 1000;

        // when
        Bond bond = new Bond(registeredServiceObligationStrategy, amountOfUnitsToPay);

        // create couple threads
        Thread r = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });
        Thread r1 = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });
        Thread r2 = new Thread(()->{
            int i = 0;
            while(i < 300){
                bond.reserveUnit();
                i++;
            }
        });

        r.start();
        r1.start();
        r2.start();

        r.join();
        r1.join();
        r2.join();

        //then
        // assertEquals(BigDecimal.valueOf(9000000, 2), bond.getBlockedBalance());

    }*/

}
