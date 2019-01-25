package com.maciejbihun.dto;
import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
public class GroupJoinRequestDto {

    private Long id;

    private Integer obligationGroupId;

    private Integer userRegisteredServiceId;

    private String proposedUnitOfWorkType;

    private Double proposedUnitOfWorkCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getObligationGroupId() {
        return obligationGroupId;
    }

    public void setObligationGroupId(Integer obligationGroupId) {
        this.obligationGroupId = obligationGroupId;
    }

    public Integer getUserRegisteredServiceId() {
        return userRegisteredServiceId;
    }

    public void setUserRegisteredServiceId(Integer userRegisteredServiceId) {
        this.userRegisteredServiceId = userRegisteredServiceId;
    }

    public String getProposedUnitOfWorkType() {
        return proposedUnitOfWorkType;
    }

    public void setProposedUnitOfWorkType(String proposedUnitOfWorkType) {
        this.proposedUnitOfWorkType = proposedUnitOfWorkType;
    }

    public Double getProposedUnitOfWorkCost() {
        return proposedUnitOfWorkCost;
    }

    public void setProposedUnitOfWorkCost(Double proposedUnitOfWorkCost) {
        this.proposedUnitOfWorkCost = proposedUnitOfWorkCost;
    }
}
