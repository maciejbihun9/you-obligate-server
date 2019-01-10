package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserAccountInObligationGroupController;
import com.maciejbihun.converters.UserAccountInObligationGroupConverter;
import com.maciejbihun.dto.UserAccountInObligationGroupDto;
import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.service.UserAccountInObligationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
@RestController(value = "/user-account-in-obligation-group")
public class UserAccountInObligationGroupControllerImpl implements UserAccountInObligationGroupController {

    @Autowired
    UserAccountInObligationGroupService userAccountInObligationGroupService;

    /**
     * Creates an account for a user in given obligation group and returns new instance of UserAccountInObligationGroup to the user with convenient http status.
     */
    @Override
    public ResponseEntity<UserAccountInObligationGroupDto> createUserAccountInObligationGroup(UserAccountInObligationGroupDto userAccountInObligationGroupDto) {
        try {
            UserAccountInObligationGroup userAccountInObligationGroup = userAccountInObligationGroupService
                    .createUserAccountInObligationGroup(userAccountInObligationGroupDto.getUsername(), userAccountInObligationGroupDto.getObligationGroupId());
            return new ResponseEntity<>(UserAccountInObligationGroupConverter.convertToDto(userAccountInObligationGroup), HttpStatus.CREATED);
        } catch (ObligationGroupDoesNotExistsException e) {
            MultiValueMap <String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("info", String.format("ObligationGroup with id: %s does not exist", userAccountInObligationGroupDto.getObligationGroupId()));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns UserAccountInObligationGroup with eagerly initialized bonds list.
     */
    @Override
    public ResponseEntity<UserAccountInObligationGroup> getUserAccountInObligationGroupWithObligationStrategies(Long userAccountInObligationGroupId) {
        UserAccountInObligationGroup userAccountInObligationGroupWithObligationStrategies =
                userAccountInObligationGroupService.getUserAccountInObligationGroupWithObligationStrategies(userAccountInObligationGroupId);
        if (userAccountInObligationGroupWithObligationStrategies == null){
            MultiValueMap <String, String> multiValueMap = new LinkedMultiValueMap();
            multiValueMap.set("info", String.format("UserAccountInObligationGroup with id: %s does not exist", userAccountInObligationGroupId));
            return new ResponseEntity<>(multiValueMap, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userAccountInObligationGroupWithObligationStrategies, HttpStatus.OK);
    }

    @Override
    @GetMapping("/user-account-balance")
    public ResponseEntity<BigDecimal> getUserAccountBalanceInGivenObligationGroup(Long userId, Long obligationGroupId) {
        BigDecimal userAccountBalanceInGivenObligationGroup =
                userAccountInObligationGroupService.getUserAccountBalanceInGivenObligationGroup(userId, obligationGroupId);
        if (userAccountBalanceInGivenObligationGroup == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userAccountBalanceInGivenObligationGroup, HttpStatus.OK);
    }
}
