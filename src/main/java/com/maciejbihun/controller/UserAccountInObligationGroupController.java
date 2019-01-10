package com.maciejbihun.controller;

import com.maciejbihun.dto.UserAccountInObligationGroupDto;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface UserAccountInObligationGroupController {

    ResponseEntity<UserAccountInObligationGroupDto> createUserAccountInObligationGroup(UserAccountInObligationGroupDto userAccountInObligationGroupDto);

    ResponseEntity<UserAccountInObligationGroup> getUserAccountInObligationGroupWithObligationStrategies(Long userAccountInObligationGroupId);

    ResponseEntity<BigDecimal> getUserAccountBalanceInGivenObligationGroup(Long obligationGroupId);

}
