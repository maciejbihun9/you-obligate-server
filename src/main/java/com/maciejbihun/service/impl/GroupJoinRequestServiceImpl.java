package com.maciejbihun.service.impl;

import com.maciejbihun.exceptions.NotEnoughPermissionsException;
import com.maciejbihun.models.GroupJoinRequest;
import com.maciejbihun.repository.GroupJoinRequestRepository;
import com.maciejbihun.service.GroupJoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Maciej Bihun
 */
@Service
public class GroupJoinRequestServiceImpl implements GroupJoinRequestService {

    @Autowired
    private GroupJoinRequestRepository groupJoinRequestRepository;

    @Override
    public GroupJoinRequest saveGroupJoinRequest(GroupJoinRequest groupJoinRequest) {
        return groupJoinRequestRepository.save(groupJoinRequest);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_DISPLAY_GROUP_JOIN_REQUESTS')")
    public List<GroupJoinRequest> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId) throws NotEnoughPermissionsException {
        return groupJoinRequestRepository.getGroupJoinRequestsByObligationGroupId(obligationGroupId);
    }

}
