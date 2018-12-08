package com.maciejbihun;

import com.maciejbihun.datatype.UnitOfWork;
import com.maciejbihun.models.*;
import com.maciejbihun.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author BHN
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private ObligationGroupRepository obligationGroupRepository;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    private RegisteredServiceObligationStrategyRepository obligationStrategyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private int amountOfUsers = 30;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setName("Maciej");
        user.setSurname("Bihun");
        user.setUsername("maciek1");
        user.setPassword(passwordEncoder.encode("maciek1"));
        user.setRoles(Arrays.asList(adminRole));

        // this is an admin for entire application, so this may stay
        userRepository.save(user);

        List<UserRegisteredService> userRegisteredServices = getUserRegisteredServices();

        List<User> users = new ArrayList<>();

        int i = 0;
        while(i < amountOfUsers) {
            User testUser = new User();
            testUser.setName("Name #" + i);
            testUser.setSurname("Surname #" + i);
            testUser.setUsername("user" + i);
            testUser.setPassword(passwordEncoder.encode("maciek1"));
            testUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
            UserRegisteredService userRegisteredService = userRegisteredServices.get(i);
            userRegisteredService.setUser(testUser);
            testUser.setUserRegisteredServices(Arrays.asList(userRegisteredService));
            users.add(testUser);
            userRepository.save(testUser);
            i++;
        }

        List<ObligationGroup> testObligationGroups = createTestObligationGroups(users.subList(0,3));

        i = 0;
        while(i < amountOfUsers){

            // create accounts for users in given obligation groups
            if (i < 5){
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(0));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);

            } else if (i >= 5 && i < 20){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(0));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);

                // create user obligation group account
                userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(1));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);

            } else if(i >= 20 && i < 25){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(1));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);

                // create user obligation group account
                userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(2));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);
            } else if (i >= 25){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(users.get(i), testObligationGroups.get(2));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(users.get(i).getUserRegisteredServices().get(0), userAccountInObligationGroup);
            }
            i++;
        }
        alreadySetup = true;
    }

    private RegisteredServiceObligationStrategy createTestObligationStrategy(UserRegisteredService userRegisteredService,
                                                                             UserAccountInObligationGroup userAccountInObligationGroup){
        // create obligation strategy
        RegisteredServiceObligationStrategy obligationStrategy = new RegisteredServiceObligationStrategy(
                userRegisteredService, userAccountInObligationGroup,
                UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 2, 1000
        );
        return obligationStrategyRepository.save(obligationStrategy);
    }

    /**
     * I assume that each obligation group will have different admin user.
     */
    private List<ObligationGroup> createTestObligationGroups(List<User> adminUsers){
        int i = 0;
        int numberOfGroups = adminUsers.size();
        List<ObligationGroup> testObligationGroups = new ArrayList<>();
        while(i < numberOfGroups){
            // create obligation group
            ObligationGroup obligationGroup = new ObligationGroup(adminUsers.get(i), "GROUP #" + i, "Money #" + i,
                    "Money #" + i, "test description");
            obligationGroup = obligationGroupRepository.save(obligationGroup);
            testObligationGroups.add(obligationGroup);
            i++;
        }
        return testObligationGroups;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    private List<UserRegisteredService> getUserRegisteredServices(){
        // people know better how to name his service,
        // people now better how to sell them self
        /*List<String> servicesNames = Arrays.asList("dentist", "hairdresser", "thai massage", "gym", "swimming pool",
                                                    "transport", "beers in a bar", "nail painting", "mowing the lawn", "personal trainer");*/
        List<UserRegisteredService> userRegisteredServices = new ArrayList<>(amountOfUsers);
        int i = 0;
        while(i < amountOfUsers){
            // create registered service
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setServiceName("SERVICE #" + i);
            userRegisteredService.setServiceDescription("Any, because it is not important now");
            userRegisteredService.setExperienceDescription("Experience description is also not really important");
            userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            userRegisteredServices.add(userRegisteredService);
            i++;
        }
        return userRegisteredServices;
    }



}