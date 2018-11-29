package com.maciejbihun.converters;

import com.maciejbihun.dto.UserDto;
import com.maciejbihun.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Maciej Bihun
 */
public class UserConverter {

    public static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public static User convertToEntity(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        return user;
    }

}
