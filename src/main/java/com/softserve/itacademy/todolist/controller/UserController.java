package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.user.*;
import com.softserve.itacademy.todolist.exception.NullEntityReferenceException;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.RoleService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    @PreAuthorize("@check.isAdmin()")
    public List<UserResponse> getAll() {
        return userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@check.isAdmin()")
    @DeleteMapping("{user_id}")
    public List<UserResponse> deleteUser(@PathVariable(name = "user_id") long userId) {
        try {
            userService.delete(userId);
            return userService.getAll().stream().map(UserResponse::new).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("@check.confirmAccessOwnerOrAdmin(#userId)")
    @PutMapping("{user_id}")
    public UserResponse updateUser(@PathVariable("user_id") Long userId, @RequestBody UserRequestDto userToUpdate) {
        try {
            User user = userService.readById(userId);
            userService.update(UserTransformer.updateEntity(
                    userToUpdate,
                    user.getRole(),
                    user));
            log.info("User with id {} has been updated", userId);
            return new UserResponse(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullEntityReferenceException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PreAuthorize("@check.confirmAccessOwnerOrAdmin(#userId)")
    @GetMapping("{user_id}")
    public UserResponse getUserById(@PathVariable(name = "user_id") long userId) {
        return new UserResponse(userService.readById(userId));
    }

    @PutMapping("/{user_id}/role")
    @PreAuthorize("@check.isAdmin()")
    public UserResponse updateUserRole(@PathVariable("user_id") long userId,
                                       @RequestBody UserRoleDto userDto) {
        try {
            User user = userService.readById(userId);
            userService.update(UserTransformer.updateUserRole(
                    roleService.readById(userDto.getRoleId()),
                    user
            ));
            return new UserResponse(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage());
        } catch (NullEntityReferenceException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
