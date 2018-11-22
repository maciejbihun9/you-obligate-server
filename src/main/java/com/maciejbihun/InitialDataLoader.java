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
    private UserGroupObligationStrategyForRegisteredServiceRepository obligationStrategyRepository;

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
        User adminUser = userRepository.save(user);

        // Add initial users
        Role userRole = roleRepository.findByName("ROLE_USER");
        int i = 0;
        List<String> names = Arrays.asList("Maciej", "Jakub", "Marian",
                "Jason", "Marlena", "Kevin", "Robert", "Malecki", "Zaneta", "Pawel");
        List<String> usernames = Arrays.asList("maciej", "jakub", "marian",
                "jason", "marlena", "kevin", "robert", "malecki", "zaneta", "pawel");

        List<UserRegisteredService> userRegisteredServices = getUserRegisteredServices();

        // create test obligation groups
        List<User> users = Arrays.asList(adminUser, userRepository.findById(2L).get(), userRepository.findById(3L).get());
        List<ObligationGroup> testObligationGroups = createTestObligationGroups(users);

        while(i < amountOfUsers){
            User testUser = new User();
            testUser.setName(names.get(i));
            testUser.setSurname("soe surname " + i);
            testUser.setUsername(usernames.get(i));
            testUser.setPassword(passwordEncoder.encode("maciek1"));
            testUser.setRoles(Arrays.asList(userRole));
            UserRegisteredService userRegisteredService = userRegisteredServices.get(i);
            userRegisteredService.setUser(testUser);
            testUser.setUserRegisteredServices(Arrays.asList(userRegisteredService));
            userRepository.save(testUser);

            // create accounts for users in given obligation groups
            if (i < 5){
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(0));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(0));

            } else if (i >= 5 && i < 20){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(0));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(0));

                // create user obligation group account
                userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(1));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(1));

            } else if(i >= 20 && i < 25){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(1));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(1));

                // create user obligation group account
                userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(2));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(2));
            } else if (i >= 25){
                // create user obligation group account
                UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(testUser, testObligationGroups.get(2));
                userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

                // create obligation strategy
                createTestObligationStrategy(testUser.getUserRegisteredServices().get(0), testObligationGroups.get(2));
            }
            i++;
        }
        alreadySetup = true;
    }

    private UserGroupObligationStrategyForRegisteredService createTestObligationStrategy(UserRegisteredService userRegisteredService, ObligationGroup obligationGroup){
        // create obligation strategy
        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                userRegisteredService, obligationGroup, UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000
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
        List<String> servicesNames = Arrays.asList("dentist", "hairdresser", "thai massage", "gym", "swimming pool",
                                                    "transport", "beers in a bar", "nail painting", "mowing the lawn", "personal trainer");
        List<UserRegisteredService> userRegisteredServices = new ArrayList<>(amountOfUsers);
        int i = 0;
        while(i < amountOfUsers){
            // create registered service
            UserRegisteredService userRegisteredService = new UserRegisteredService();
            userRegisteredService.setCreatedDateTime(LocalDateTime.now());
            userRegisteredService.setServiceName(servicesNames.get(i));
            userRegisteredService.setServiceDescription("Any, because it is not important now");
            userRegisteredService.setExperienceDescription("Experience desciption is also not really important");
            userRegisteredService.setUserRegisteredServiceCategory(UserRegisteredServiceCategory.IT);
            userRegisteredServices.add(userRegisteredService);
            i++;
        }
        return userRegisteredServices;
    }



}