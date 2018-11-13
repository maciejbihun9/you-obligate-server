package com.maciejbihun.dto;

import com.maciejbihun.models.Bond;
import com.maciejbihun.models.ObligationGroup;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ObligationGroupAccountDto {

    private Long id;

    private String username;

    private Long obligationGroupId;

    public ObligationGroupAccountDto(String username, Long obligationGroupId) {
        this.username = username;
        this.obligationGroupId = obligationGroupId;
    }

    public String getUsername() {
        return username;
    }

    public Long getObligationGroupId() {
        return obligationGroupId;
    }
}
