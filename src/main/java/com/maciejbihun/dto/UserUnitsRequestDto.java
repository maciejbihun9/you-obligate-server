package com.maciejbihun.dto;

import com.maciejbihun.models.UserRegisteredService;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author BHN
 */
public class UserUnitsRequestDto {

    private Long userRegisteredServiceId;

    public Long getUserRegisteredServiceId() {
        return userRegisteredServiceId;
    }

    public void setUserRegisteredServiceId(Long userRegisteredServiceId) {
        this.userRegisteredServiceId = userRegisteredServiceId;
    }
}
