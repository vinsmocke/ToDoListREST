package com.softserve.itacademy.todolist.dto.user;

import com.softserve.itacademy.todolist.exception.FailedAccessException;
import com.softserve.itacademy.todolist.model.Role;
import com.softserve.itacademy.todolist.model.User;

public class UserTransformer {
    public static UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getPassword()
        );
    }

    public static User convertToEntity(UserDto userDto, Role role) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(role);
        return user;
    }

    public static User updateEntity(UserRequestDto userRequestDto, Role role, User user) {
        if (!userRequestDto.getFirstName().isBlank()){
            user.setFirstName(userRequestDto.getFirstName());
        }
        if (!userRequestDto.getLastName().isBlank()){
            user.setLastName(userRequestDto.getLastName());
        }
        if (userRequestDto.getEmail() != null){
            user.setEmail(userRequestDto.getEmail());
        }
        if (!userRequestDto.getPassword().isBlank()){
            user.setPassword(userRequestDto.getPassword());
        }
        if (userRequestDto.getRoleId() != null){
            user.setRole(role);
        }
        return user;
    }

    public static User updateUserRole(Role role, User user){
        if (user.getRole().getName().equals(role.getName())){
            throw new IllegalArgumentException(user.getFirstName() + " already has role " + role.getName());
        }
        user.setRole(role);
        return user;
    }
}
