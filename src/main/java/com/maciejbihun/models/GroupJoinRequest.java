package com.maciejbihun.models;

import com.maciejbihun.datatype.UnitOfWork;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Maciej Bihun
 */
@Entity
@Table(name = "GroupJoinRequest")
public class GroupJoinRequest {

    @Id
    @Column(name = "ID", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GROUP_JOIN_REQUEST_SEQ")
    @SequenceGenerator(name = "GROUP_JOIN_REQUEST_SEQ", sequenceName = "GROUP_JOIN_REQUEST_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OBLIGATION_GROUP_ID", updatable = false, nullable = false)
    private ObligationGroup obligationGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_REGISTERED_SERVICE_ID", updatable = false, nullable = false)
    private UserRegisteredService userRegisteredService;

    @Basic(optional = false)
    @Column(name = "PROPOSED_UNIT_OF_WORK_TYPE", updatable = false, nullable = false)
    private UnitOfWork proposedUnitOfWorkType;

    @Basic(optional = false)
    @Column(name = "PROPOSED_UNIT_OF_WORK_COST", updatable = false, nullable = false)
    private BigDecimal proposedUnitOfWorkCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public UserRegisteredService getUserRegisteredService() {
        return userRegisteredService;
    }

    public void setUserRegisteredService(UserRegisteredService userRegisteredService) {
        this.userRegisteredService = userRegisteredService;
    }

    public UnitOfWork getProposedUnitOfWorkType() {
        return proposedUnitOfWorkType;
    }

    public void setProposedUnitOfWorkType(UnitOfWork proposedUnitOfWorkType) {
        this.proposedUnitOfWorkType = proposedUnitOfWorkType;
    }

    public BigDecimal getProposedUnitOfWorkCost() {
        return proposedUnitOfWorkCost;
    }

    public void setProposedUnitOfWorkCost(BigDecimal proposedUnitOfWorkCost) {
        this.proposedUnitOfWorkCost = proposedUnitOfWorkCost;
    }
}
