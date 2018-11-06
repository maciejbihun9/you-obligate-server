package com.maciejbihun;

import com.maciejbihun.models.Privilege;
import com.maciejbihun.models.Role;
import com.maciejbihun.models.User;
import com.maciejbihun.repository.PrivilegeRepository;
import com.maciejbihun.repository.RoleRepository;
import com.maciejbihun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
        while(i < 10){
            User testUser = new User();
            testUser.setName(names.get(i));
            testUser.setUsername(usernames.get(i));
            testUser.setPassword(passwordEncoder.encode("maciek1"));
            testUser.setRoles(Arrays.asList(userRole));
            i++;
        }
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
}