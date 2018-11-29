package com.maciejbihun.converters;

import com.maciejbihun.dto.UserRegisteredServiceDto;
import com.maciejbihun.models.UserRegisteredService;

public class UserRegisteredServiceConverter {

    public static UserRegisteredServiceDto convertToDto(UserRegisteredService userRegisteredService){
        UserRegisteredServiceDto userRegisteredServiceDto = new UserRegisteredServiceDto();
        userRegisteredServiceDto.setId(userRegisteredService.getId());
        userRegisteredServiceDto.setCreatedDateTime(userRegisteredService.getCreatedDateTime());
        userRegisteredServiceDto.setExperienceDescription(userRegisteredService.getExperienceDescription());
        userRegisteredServiceDto.setServiceDescription(userRegisteredService.getServiceDescription());
        userRegisteredServiceDto.setServiceName(userRegisteredService.getServiceName());
        userRegisteredServiceDto.setUserRegisteredServiceCategory(userRegisteredService.getUserRegisteredServiceCategory());
        return userRegisteredServiceDto;
    }

    public static UserRegisteredService convertToEntity(UserRegisteredServiceDto userRegisteredServiceDto){
        UserRegisteredService userRegisteredService = new UserRegisteredService();
        userRegisteredService.setExperienceDescription(userRegisteredServiceDto.getExperienceDescription());
        userRegisteredService.setServiceDescription(userRegisteredServiceDto.getServiceDescription());
        userRegisteredService.setServiceName(userRegisteredServiceDto.getServiceName());
        userRegisteredService.setUserRegisteredServiceCategory(userRegisteredServiceDto.getUserRegisteredServiceCategory());
        userRegisteredService.setUser(UserConverter.convertToEntity(userRegisteredServiceDto.getUserDto()));
        return userRegisteredService;
    }

}
