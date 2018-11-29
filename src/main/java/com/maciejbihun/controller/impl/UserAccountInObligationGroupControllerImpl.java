package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserAccountInObligationGroupController;
import com.maciejbihun.converters.UserAccountInObligationGroupConverter;
import com.maciejbihun.dto.UserAccountInObligationGroupDto;
import com.maciejbihun.exceptions.ObligationGroupDoesNotExistsException;
import com.maciejbihun.models.UserAccountInObligationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Maciej Bihun
 */
@Controller
public class UserAccountInObligationGroupControllerImpl implements UserAccountInObligationGroupController {

    @Autowired
    com.maciejbihun.service.UserAccountInObligationGroupService userAccountInObligationGroupService;

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
}
