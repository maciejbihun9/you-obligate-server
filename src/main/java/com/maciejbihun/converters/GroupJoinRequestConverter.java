package com.maciejbihun.converters;

import com.maciejbihun.dto.BondDto;
import com.maciejbihun.dto.GroupJoinRequestDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.GroupJoinRequest;

/**
 * @author Maciej Bihun
 */
public class GroupJoinRequestConverter {

    public static GroupJoinRequestDto convertToDto(GroupJoinRequest groupJoinRequest){
        return new GroupJoinRequestDto();
    }

    public static GroupJoinRequest convertToEntity(GroupJoinRequestDto groupJoinRequestDto){
        return null;
    }

}
