package com.maciejbihun.dto;

public class ObligationGroupDto {

    Long id;

    private UserDto ownerDto;

    private String name;

    private String moneyName;

    private String moneyShortcutName;

    private String description;

    private String amountOfCreatedMoney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(UserDto ownerDto) {
        this.ownerDto = ownerDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName;
    }

    public String getMoneyShortcutName() {
        return moneyShortcutName;
    }

    public void setMoneyShortcutName(String moneyShortcutName) {
        this.moneyShortcutName = moneyShortcutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmountOfCreatedMoney() {
        return amountOfCreatedMoney;
    }

    public void setAmountOfCreatedMoney(String amountOfCreatedMoney) {
        this.amountOfCreatedMoney = amountOfCreatedMoney;
    }
}
