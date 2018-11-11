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

    private User user;

    private BigDecimal accountBalance = new BigDecimal("0.00");

    private ObligationGroup obligationGroup;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    private List<Bond> bonds;

    private List<UserGroupObligationStrategyForRegisteredService> userObligationStrategies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ObligationGroup getObligationGroup() {
        return obligationGroup;
    }

    public void setObligationGroup(ObligationGroup obligationGroup) {
        this.obligationGroup = obligationGroup;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public void setBonds(List<Bond> bonds) {
        this.bonds = bonds;
    }

    public List<UserGroupObligationStrategyForRegisteredService> getUserObligationStrategies() {
        return userObligationStrategies;
    }

    public void setUserObligationStrategies(List<UserGroupObligationStrategyForRegisteredService> userObligationStrategies) {
        this.userObligationStrategies = userObligationStrategies;
    }
}
