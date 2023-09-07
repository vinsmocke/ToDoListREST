package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.todo.ToDoCreateResponse;
import com.softserve.itacademy.todolist.dto.todo.ToDoRequestDto;
import com.softserve.itacademy.todolist.dto.todo.ToDoResponseDto;
import com.softserve.itacademy.todolist.dto.todo.ToDoTransformer;
import com.softserve.itacademy.todolist.dto.user.UserResponse;
import com.softserve.itacademy.todolist.exception.EntityAlreadyExistsException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{u_id}/todos")
@AllArgsConstructor
public class ToDoController {
    private UserService userService;
    private ToDoService todoService;

    @GetMapping()
    @PreAuthorize("@check.isOwner(#userId)")
    public List<ToDoResponseDto> readTodosOfUser(@PathVariable("u_id") long userId) {
        List<ToDo> todos = userService.readById(userId).getMyTodos();
        todos.addAll(userService.readById(userId).getOtherTodos());
        return todos.stream().map(ToDoResponseDto::new).collect(Collectors.toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@check.isOwner(#userId)")
    public ToDoCreateResponse createTodo(@PathVariable("u_id") long userId, @RequestBody() ToDoRequestDto toDoDto) {
        return new ToDoCreateResponse(todoService.create(ToDoTransformer.convertToEntity(toDoDto, userService.readById(userId))));
    }

    @PreAuthorize("@check.isOwnerOrCollaboratorOfToDo(#todoId)")
    @GetMapping("/{t_id}")
    public ToDoResponseDto readTodoOfUser(@PathVariable("u_id") long userId, @PathVariable("t_id") long todoId) {
        return new ToDoResponseDto(todoService.readById(todoId));
    }

    @PreAuthorize("@check.isOwnerOfToDo(#todoId)")
    @PutMapping("/{t_id}")
    public ToDoResponseDto updateToDo(@PathVariable("t_id") long todoId, @PathVariable("u_id") long userId,
                                      @RequestBody() ToDoRequestDto toDoDto) {

        ToDo toDo = todoService.readById(todoId);
        toDo.setTitle(toDoDto.getTitle());
        toDo.setCreatedAt(LocalDateTime.now());
        return new ToDoResponseDto(todoService.update(toDo));
    }

    @PreAuthorize("@check.isOwnerOfToDo(#todoId)")
    @DeleteMapping("/{t_id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ToDoResponseDto> deleteToDo(@PathVariable("u_id") long userId, @PathVariable("t_id") long todoId) {
        todoService.delete(todoId);
        return todoService.getByUserId(userId).stream().map(ToDoResponseDto::new).collect(Collectors.toList());
    }

    @PreAuthorize("@check.isOwnerOrCollaboratorOfToDo(#todoId)")
    @GetMapping("/{t_id}/collaborators")
    public List<UserResponse> getAllCollaborator(@PathVariable("u_id") long userId, @PathVariable("t_id") long todoId) {
        ToDo todo = todoService.readById(todoId);
        return todo.getCollaborators().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @PreAuthorize("@check.isOwnerOfToDo(#todoId)")
    @PostMapping("/{t_id}/collaborators/{c_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserResponse> addCollaborator(@PathVariable("t_id") long todoId, @PathVariable("u_id") long userId,
                                              @PathVariable("c_id") long collaboratorId) {
        ToDo todo = todoService.readById(todoId);
        List<User> collaborators = todo.getCollaborators();
        if (todo.getOwner().getId() == collaboratorId || collaborators.stream().anyMatch(c -> c.getId() == collaboratorId)) {
            throw new EntityAlreadyExistsException("This person already works on this todo");
        }
        collaborators.add(userService.readById(collaboratorId));
        todo.setCollaborators(collaborators);
        return todoService.update(todo).getCollaborators().stream().map(UserResponse::new).toList();
    }

    @PreAuthorize("@check.isOwnerOfToDo(#todoId)")
    @DeleteMapping("/{t_id}/collaborators/{c_id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCollaborator(@PathVariable("t_id") long todoId, @PathVariable("u_id") long userId,
                                   @PathVariable("c_id") long collaboratorId) {
        ToDo todo = todoService.readById(todoId);
        List<User> collaborators = todo.getCollaborators();
        collaborators.remove(userService.readById(collaboratorId));
        todo.setCollaborators(collaborators);
        todoService.update(todo);
    }
}
