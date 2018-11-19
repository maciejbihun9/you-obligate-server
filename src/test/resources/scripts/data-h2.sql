-- create privilege entities --
INSERT INTO Privilege (ID, NAME) VALUES (1, 'READ_PRIVILEGE');
INSERT INTO Privilege (ID, NAME) VALUES (2, 'WRITE_PRIVILEGE');

-- create role entities --
INSERT INTO Role (ID, NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO Role (ID, NAME) VALUES (2, 'ROLE_USER');

-- create roles_privileges entities --
INSERT INTO Roles_Privileges (ROLE_ID, PRIVILEGE_ID) VALUES (1, 1);
INSERT INTO Roles_Privileges (ROLE_ID, PRIVILEGE_ID) VALUES (1, 2);
INSERT INTO Roles_Privileges (ROLE_ID, PRIVILEGE_ID) VALUES (2, 1);

-- create User entities --
INSERT INTO User_ (ID, NAME, SURNAME, USERNAME, PASSWORD) VALUES (1, 'Maciej', 'Bihun', 'maciek', 'maciek1');
INSERT INTO User_ (ID, NAME, SURNAME, USERNAME, PASSWORD) VALUES (2, 'Jakub', 'Bihun', 'jakub', 'jakub1');
INSERT INTO User_ (ID, NAME, SURNAME, USERNAME, PASSWORD) VALUES (3, 'Natalia', 'Bihun', 'natalia', 'natalia1');

-- create Users_Roles entities --
INSERT INTO Users_Roles (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO Users_Roles (USER_ID, ROLE_ID) VALUES (1, 2);
INSERT INTO Users_Roles (USER_ID, ROLE_ID) VALUES (2, 2);
INSERT INTO Users_Roles (USER_ID, ROLE_ID) VALUES (3, 2);

-- create obligation group
INSERT INTO ObligationGroup (ID, GROUP_OWNER_ID, GROUP_NAME, MONEY_NAME, MONEY_SHORTCUT_NAME, GROUP_DESCRIPTION, CREATED_DATE_TIME, ACCOUNT_BALANCE)
VALUES (1, 2, 'SPARTANS', 'BHN', 'BIHUN', 'This is just simple name', null, 0);

-- create UserAccountInObligationGroup entity --
INSERT INTO UserAccountInObligationGroup (ID, USER_ID, ACCOUNT_BALANCE, OBLIGATION_GROUP_ID, OBLIGATION_GROUP_CREATED_DATE_TIME)
VALUES (1, 2, '0.00', 1, '1993-04-26 12:12:12');

-- create UserRegisteredService entity --
INSERT INTO UserRegisteredService (ID, CREATED_DATE_TIME, SERVICE_EXPERIENCE_DESCRIPTION,
   SERVICE_DESCRIPTION, SERVICE_NAME, SERVICE_CATEGORY, USER_ID)
VALUES (1, '1993-04-26 12:12:12', 'this is an experience description', 'service description', 'service name', 'category', 1);

-- create UserAccountInObligationGroup entity --
INSERT INTO UserGroupObligationStrategyForRegisteredService (ID, USER_REGISTERED_SERVICE_ID, OBLIGATION_GROUP_ID, UNIT_OF_WORK,
UNIT_OF_WORK_COST, INTEREST_RATE, ALREADY_CREATED_AMOUNT_OF_MONEY, ALREADY_OBLIGATED_UNITS_OF_WORK, AMOUNT_OF_UNITS_EVER_PAID,
 MIN_AMOUNT_OF_UNITS_PER_BOND, DEBT_UNITS_LIMIT, MAX_AMOUNT_OF_UNITS_FOR_OBLIGATION)

VALUES (1,1,1, 1, DECIMAL(100,2), 0.05, 0.00, 0, 0, 1, 1000, 200);
