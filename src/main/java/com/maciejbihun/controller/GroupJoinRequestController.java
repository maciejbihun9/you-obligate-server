package com.maciejbihun.controller;

import com.maciejbihun.dto.GroupJoinRequestDto;
import com.maciejbihun.models.GroupJoinRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author maciej Bihun
 */
public interface GroupJoinRequestController {

    ResponseEntity<GroupJoinRequest> createGroupJoinRequest(GroupJoinRequestDto groupJoinRequestDto);
    ResponseEntity<GroupJoinRequest> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId);

}
