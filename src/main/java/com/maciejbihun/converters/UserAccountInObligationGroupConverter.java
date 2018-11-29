package com.maciejbihun.converters;

import com.maciejbihun.dto.UserAccountInObligationGroupDto;
import com.maciejbihun.models.UserAccountInObligationGroup;

public class UserAccountInObligationGroupConverter {

    public static UserAccountInObligationGroupDto convertToDto(UserAccountInObligationGroup userAccountInObligationGroup){
        return new UserAccountInObligationGroupDto(userAccountInObligationGroup.getUser().getUsername(),
                userAccountInObligationGroup.getObligationGroup().getId());
    }

    public static UserAccountInObligationGroup convertToEntity(UserAccountInObligationGroupDto userAccountInObligationGroupDto){
        return null;
    }

}
