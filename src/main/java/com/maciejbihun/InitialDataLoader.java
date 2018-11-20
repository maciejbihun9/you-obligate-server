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

    private int amountOfUsers = 10;

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
        User adminUser = userRepository.save(user);

        // Add initial users
        Role userRole = roleRepository.findByName("ROLE_USER");
        int i = 0;
        List<String> names = Arrays.asList("Maciej", "Jakub", "Marian",
                "Jason", "Marlena", "Kevin", "Robert", "Malecki", "Zaneta", "Pawel");
        List<String> usernames = Arrays.asList("maciej", "jakub", "marian",
                "jason", "marlena", "kevin", "robert", "malecki", "zaneta", "pawel");

        List<UserRegisteredService> userRegisteredServices = getUserRegisteredServices();

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
            i++;
        }

        // create obligation group
        ObligationGroup obligationGroup = new ObligationGroup(adminUser, "SPARTANS", "Bihun",
                "BHN", "This is just simple little money name");
        obligationGroupRepository.save(obligationGroup);

        // create obligation group account
        User userById = userRepository.findById(2L).get();
        UserAccountInObligationGroup userAccountInObligationGroup = new UserAccountInObligationGroup(userById, obligationGroup);
        userAccountInObligationGroupRepository.save(userAccountInObligationGroup);

        // create obligation strategy
        UserGroupObligationStrategyForRegisteredService obligationStrategy = new UserGroupObligationStrategyForRegisteredService(
                userById.getUserRegisteredServices().get(0), obligationGroup, UnitOfWork.SERVICE, new BigDecimal("100.00"), new BigDecimal("0.05"), 1000
        );
        obligationStrategyRepository.save(obligationStrategy);

        alreadySetup = true;
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