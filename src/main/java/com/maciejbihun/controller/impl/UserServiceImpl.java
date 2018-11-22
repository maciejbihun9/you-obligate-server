package com.maciejbihun.controller.impl;

import com.maciejbihun.controller.UserService;
import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.User;
import com.maciejbihun.models.UserGroupObligationStrategyForRegisteredService;
import com.maciejbihun.models.UserPrincipal;
import com.maciejbihun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author BHN
 */
@Controller
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUserAccount(@RequestBody UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> saveUserData(@RequestBody UserDto userDto){
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> saveUserData(@RequestBody User user){
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public UserPrincipal loadUserByUsername(@RequestParam("username") String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    @Override
    public List<User> getUsersWithManyAccounts() {
        return null;
    }

}
