package com.maciejbihun.converters;

import com.maciejbihun.dto.ObligationGroupDto;
import com.maciejbihun.models.ObligationGroup;

/**
 * @author Maciej Bihun
 */
public class ObligationGroupConverter {

    public static ObligationGroupDto convertToDto(ObligationGroup obligationGroup){
        ObligationGroupDto obligationGroupDto = new ObligationGroupDto();
        if (obligationGroup.getId() != null){
            obligationGroupDto.setId(obligationGroup.getId());
        }
        obligationGroupDto.setName(obligationGroup.getName());
        obligationGroupDto.setMoneyName(obligationGroup.getMoneyName());
        obligationGroupDto.setMoneyShortcutName(obligationGroup.getMoneyShortcutName());
        obligationGroupDto.setDescription(obligationGroup.getDescription());

        obligationGroupDto.setOwnerDto(UserConverter.convertToDto(obligationGroup.getOwner()));
        return obligationGroupDto;
    }

    public static ObligationGroup convertToEntity(ObligationGroupDto obligationGroupDto){
        ObligationGroup obligationGroup = new ObligationGroup();
        obligationGroup.setOwner(UserConverter.convertToEntity(obligationGroupDto.getOwnerDto()));
        obligationGroup.setName(obligationGroupDto.getName());
        obligationGroup.setDescription(obligationGroupDto.getDescription());
        obligationGroup.setMoneyName(obligationGroupDto.getMoneyName());
        obligationGroup.setMoneyShortcutName(obligationGroupDto.getMoneyShortcutName());
        return obligationGroup;
    }

}
