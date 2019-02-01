package com.maciejbihun.service;

import com.maciejbihun.models.GroupJoinRequest;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

/**
 * @author Maciej Bihun
 */
public interface GroupJoinRequestService {

    GroupJoinRequest saveGroupJoinRequest(GroupJoinRequest groupJoinRequest);

    List<GroupJoinRequest> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId) throws AccessDeniedException;

}
