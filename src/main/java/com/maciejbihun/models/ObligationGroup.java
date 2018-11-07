package com.maciejbihun.models;

import java.time.LocalDateTime;
import java.util.List;

public class ObligationGroup {

    Long id;
    private User owner;
    private String name;
    private String moneyName;
    private String moneyShortcutName;
    // private Image image;
    private String description;
    private LocalDateTime createdDate;
    private Long amountOfCreatedMoney;
    private List<UserRegisteredService> registeredServices;
    private List<User>members;
    private List<GroupWantedService> wantedServices;

}
