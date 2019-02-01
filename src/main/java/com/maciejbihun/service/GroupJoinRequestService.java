package com.maciejbihun.service;

import com.maciejbihun.exceptions.NotEnoughPermissionsException;
import com.maciejbihun.models.GroupJoinRequest;

import java.util.List;

/**
 * @author Maciej Bihun
 */
public interface GroupJoinRequestService {

    GroupJoinRequest saveGroupJoinRequest(GroupJoinRequest groupJoinRequest);

    List<GroupJoinRequest> getGroupJoinRequestsByObligationGroupId(Integer obligationGroupId) throws NotEnoughPermissionsException;

}
