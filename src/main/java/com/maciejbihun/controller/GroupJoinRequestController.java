package com.maciejbihun.controller;

import com.maciejbihun.dto.GroupJoinRequestDto;
import com.maciejbihun.models.GroupJoinRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author maciej Bihun
 */
public interface GroupJoinRequestController {

    ResponseEntity<GroupJoinRequest> createGroupJoinRequest(GroupJoinRequestDto groupJoinRequestDto);
    ResponseEntity<List<GroupJoinRequest>> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId);

}
