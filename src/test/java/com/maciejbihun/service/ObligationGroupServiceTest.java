package com.maciejbihun.service;

import com.maciejbihun.models.*;
import com.maciejbihun.service.impl.ObligationGroupServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.User;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ObligationGroupServiceTest {

    private ObligationGroupService obligationGroupService;

    @Before
    public void setUp(){
        obligationGroupService = new ObligationGroupServiceImpl();
    }

    @Test
    public void recommendObligationGroupsForUser(){
        Set<ServiceTag> expectedServicesTags = new HashSet<>();
        int i = 0;
        while(i < 7){
            ServiceTag serviceTag = new ServiceTag();
            serviceTag.setValue("TAG" + i);
            expectedServicesTags.add(serviceTag);
            i++;
        }

        List<ObligationGroup> obligationGroups = new ArrayList<>();
        int j = 0;

        // Each obligation group will have only one account, one registered service, but they will differ in amount of tags that were attached to registered services
        while(j < 10){
            ObligationGroup obligationGroupMock = mock(ObligationGroup.class);
            UserAccountInObligationGroup userAccountInObligationGroupMock = mock(UserAccountInObligationGroup.class);
            RegisteredServiceObligationStrategy registeredServiceObligationStrategyMock = mock(RegisteredServiceObligationStrategy.class);
            UserRegisteredService userRegisteredServiceMock = mock(UserRegisteredService.class);
            if (j == 0){
                ServiceTag serviceTag1 = new ServiceTag();
                serviceTag1.setValue("TAG4");
                ServiceTag serviceTag2 = new ServiceTag();
                serviceTag2.setValue("TAG5");
                ServiceTag serviceTag3 = new ServiceTag();
                serviceTag3.setValue("TAG6");
                Mockito.when(userRegisteredServiceMock.getUserRegisteredServiceTags())
                        .thenReturn(new HashSet<>(new ArrayList<>(Arrays.asList(serviceTag1, serviceTag2, serviceTag3))));
            } else if (j == 5){
                ServiceTag serviceTag1 = new ServiceTag();
                serviceTag1.setValue("TAG1");
                ServiceTag serviceTag2 = new ServiceTag();
                serviceTag2.setValue("TAG2");
                Mockito.when(userRegisteredServiceMock.getUserRegisteredServiceTags())
                        .thenReturn(new HashSet<>(new ArrayList<>(Arrays.asList(serviceTag1, serviceTag2))));
            } else {
                ServiceTag serviceTag1 = new ServiceTag();
                serviceTag1.setValue("TAG7");
                Mockito.when(userRegisteredServiceMock.getUserRegisteredServiceTags())
                        .thenReturn(new HashSet<>(new ArrayList<>(Arrays.asList(serviceTag1))));
            }
            Mockito.when(registeredServiceObligationStrategyMock.getUserRegisteredService()).thenReturn(userRegisteredServiceMock);
            Mockito.when(userAccountInObligationGroupMock.getUserObligationStrategies())
                    .thenReturn(new HashSet<>(new ArrayList<>(Arrays.asList(registeredServiceObligationStrategyMock))));
            Mockito.when(obligationGroupMock.getUserAccountsInObligationGroup())
                    .thenReturn(new HashSet<>(new ArrayList<>(Arrays.asList(userAccountInObligationGroupMock))));
            obligationGroups.add(obligationGroupMock);
            j++;
        }
        List<ObligationGroup> recommendObligationGroups = obligationGroupService.getRecommendObligationGroups(expectedServicesTags, obligationGroups);
        Set<ServiceTag> userRegisteredServiceTags =
                recommendObligationGroups.get(0).getUserAccountsInObligationGroup().iterator().next().getUserObligationStrategies()
                        .iterator().next().getUserRegisteredService().getUserRegisteredServiceTags();

        assertEquals(3, userRegisteredServiceTags.size());
    }

}

