package com.maciejbihun.controller;

import com.maciejbihun.dto.ObligationGroupAccountDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserAccountInObligationGroupController {

    /**
     * Creates an account in a ObligationGroup for given user.
     */
    ResponseEntity<UserAccountInObligationGroup> createGroupAccount(ObligationGroupAccountDto obligationGroupAccountDto);

    ResponseEntity<UserAccountInObligationGroup> getUserAccountInObligationGroupWithBonds(Long userAccountInObligationGroupId);

}
