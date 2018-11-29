package com.maciejbihun.dto;

public class UserAccountInObligationGroupDto {

    private Long id;

    private String username;

    private Long obligationGroupId;

    public UserAccountInObligationGroupDto(String username, Long obligationGroupId) {
        this.username = username;
        this.obligationGroupId = obligationGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Long getObligationGroupId() {
        return obligationGroupId;
    }
}
