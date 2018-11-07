package com.maciejbihun;

import com.maciejbihun.models.*;
import com.maciejbihun.repository.PrivilegeRepository;
import com.maciejbihun.repository.RoleRepository;
import com.maciejbihun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author BHN
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

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
        userRepository.save(user);

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
            testUser.setUsername(usernames.get(i));
            testUser.setPassword(passwordEncoder.encode("maciek1"));
            testUser.setRoles(Arrays.asList(userRole));
            testUser.setUserRegisteredServices(Arrays.asList(userRegisteredServices.get(i)));
            userRepository.save(testUser);
            i++;
        }
        alreadySetup = true;
    }

    private List<UserRegisteredService> getUserRegisteredServices(){
        // people know better how to name his service,
        // people now better how to sell themselfs
        List<String> servicesNames = Arrays.asList("hairdresser", "dentist", "thai massage", "gym", "swimming pool",
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
            i++;
        }
        return userRegisteredServices;
    }

    private void setUsersRegisteredServices(){
        // it would be nice if everybody have different registered service
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
}