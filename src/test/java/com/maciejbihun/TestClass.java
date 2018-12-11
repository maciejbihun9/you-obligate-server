package com.maciejbihun;

import com.maciejbihun.models.ServiceTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestClass {

    @Test
    public void testMethod(){

        ServiceTag serviceTag1 = new ServiceTag();
        serviceTag1.setValue("TAG1");

        ServiceTag serviceTag2 = new ServiceTag();
        serviceTag2.setValue("TAG2");

        ServiceTag serviceTag3 = new ServiceTag();
        serviceTag3.setValue("TAG3");

        Set<ServiceTag> serviceTagSet = new HashSet<>(Arrays.asList(serviceTag1, serviceTag2, serviceTag3));
        System.out.println(serviceTagSet);
    }

}
