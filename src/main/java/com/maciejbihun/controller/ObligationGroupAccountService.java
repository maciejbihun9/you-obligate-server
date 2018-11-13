package com.maciejbihun.controller;

import com.maciejbihun.dto.ObligationGroupAccountDto;
import com.maciejbihun.models.ObligationGroupAccount;
import org.springframework.http.ResponseEntity;

public interface ObligationGroupAccountService {

    /**
     * Creates an account in a ObligationGroup for given user.
     */
    ResponseEntity<ObligationGroupAccount> createGroupAccount(ObligationGroupAccountDto obligationGroupAccountDto);

}
