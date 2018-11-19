package com.maciejbihun;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class BigDecimalThreadingTest {

    private BigDecimal bigDecimal = new BigDecimal("100");
    private final AtomicReference<BigDecimal> bigDecimalAtomic = new AtomicReference<>(BigDecimal.ZERO);

    @Test
    public void testMethod() throws InterruptedException {
        Thread one = new Thread(() -> {
            int i = 0;
            while(i < 10){
                bigDecimal = bigDecimal.add(new BigDecimal("1"));
                bigDecimalAtomic.updateAndGet(bigDecimal -> bigDecimal.add(BigDecimal.valueOf(1)));
                i++;
            }
        });
        one.start();
        Thread two = new Thread(() -> {
            int i = 0;
            while(i < 10){
                bigDecimal = bigDecimal.add(new BigDecimal("1"));
                bigDecimalAtomic.updateAndGet(bigDecimal -> bigDecimal.add(BigDecimal.valueOf(1)));
                i++;
            }
        });
        two.start();
        Thread three = new Thread(() -> {
            int i = 0;
            while(i < 10){
                bigDecimal = bigDecimal.add(new BigDecimal("1"));
                bigDecimalAtomic.updateAndGet(bigDecimal -> bigDecimal.add(BigDecimal.valueOf(1)));
                i++;
            }
        });
        three.start();

        one.join();
        two.join();
        three.join();


        System.out.println();
    }

}
