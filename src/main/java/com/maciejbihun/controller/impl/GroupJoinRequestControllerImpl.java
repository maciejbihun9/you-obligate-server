package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.GroupJoinRequestController;
import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.dto.GroupJoinRequestDto;
import com.maciejbihun.models.GroupJoinRequest;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.UserRegisteredService;
import com.maciejbihun.service.GroupJoinRequestService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
@Controller(value = "/group-join-requests")
public class GroupJoinRequestControllerImpl implements GroupJoinRequestController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GroupJoinRequestService groupJoinRequestService;

    @Override
    @PostMapping(value = "/create-group-join-request")
    public ResponseEntity<GroupJoinRequest> createGroupJoinRequest(@RequestBody GroupJoinRequestDto groupJoinRequestDto) {
        Session session = entityManager.unwrap(Session.class);
        ObligationGroup obligationGroup = session.load(ObligationGroup.class, groupJoinRequestDto.getObligationGroupId());
        UserRegisteredService userRegisteredService =
                session.load(UserRegisteredService.class, groupJoinRequestDto.getUserRegisteredServiceId());
        GroupJoinRequest groupJoinRequest = new GroupJoinRequest();
        groupJoinRequest.setObligationGroup(obligationGroup);
        groupJoinRequest.setUserRegisteredService(userRegisteredService);
        groupJoinRequest.setProposedUnitOfWorkType(UnitOfWork.valueOf(groupJoinRequestDto.getProposedUnitOfWorkType()));
        groupJoinRequest.setProposedUnitOfWorkCost(BigDecimal.valueOf(groupJoinRequestDto.getProposedUnitOfWorkCost()));
        groupJoinRequest = groupJoinRequestService.saveGroupJoinRequest(groupJoinRequest);
        return new ResponseEntity<>(groupJoinRequest, HttpStatus.CREATED);
    }

}
