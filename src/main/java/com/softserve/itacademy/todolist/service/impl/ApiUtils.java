package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.exception.FailedAccessException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.UserRepository;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("check")
public class ApiUtils {

    private final UserService userService;
    private final ToDoService todoService;

    @Autowired

    public ApiUtils(UserService userService, ToDoService toDoService) {
        this.userService = userService;
        this.todoService = toDoService;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().
                stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
    }

    public boolean isOwner(long userId) {
        User currentUser = userService.readById(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(currentUser.getEmail());
    }

    public boolean isOwnerOfToDo(long todoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().equals(todoService.readById(todoId).getOwner().getEmail());
    }

    public boolean isOwnerOrCollaboratorOfToDo(long todoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ToDo todo = todoService.readById(todoId);
        return (authentication.getName().equals(todoService.readById(todoId).getOwner().getEmail())
                || todo.getCollaborators()
                .stream()
                .anyMatch(collaborator -> collaborator.getEmail().equals(authentication.getName())));
    }

    public boolean confirmAccessOwnerOrAdmin(long userId) {
        return isAdmin() || isOwner(userId);
    }
}
