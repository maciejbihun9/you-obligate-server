package com.maciejbihun.service.impl;

import com.maciejbihun.models.GroupJoinRequest;
import com.maciejbihun.repository.GroupJoinRequestRepository;
import com.maciejbihun.service.GroupJoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
