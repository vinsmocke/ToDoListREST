package com.softserve.itacademy.todolist.dto.todo;

import com.softserve.itacademy.todolist.dto.task.TaskDto;
import com.softserve.itacademy.todolist.dto.task.TaskTransformer;
import com.softserve.itacademy.todolist.dto.user.UserTransformer;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ToDoTransformer {
    public static ToDoDto convertToDto(ToDo todo) {
        return new ToDoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getCreatedAt(),
                UserTransformer.convertToDto(todo.getOwner()),
                (todo.getTasks() != null)
                        ? todo.getTasks().stream().map(task -> TaskTransformer.convertToDto(task)).collect(Collectors.toList())
                        : new ArrayList<>(),
                (todo.getCollaborators() != null)
                        ? todo.getCollaborators().stream().map(user -> UserTransformer.convertToDto(user)).collect(Collectors.toList())
                        : new ArrayList<>()

        );
    }

    public static ToDo convertToEntity(ToDoDto toDoDto) {
        ToDo toDo = new ToDo();
        toDo.setId(toDoDto.getId());
        toDo.setTitle(toDoDto.getTitle());
        toDo.setTasks(toDoDto.getTasks().stream().map(task -> convertTaskDtoToEntity(task)).collect(Collectors.toList()));
        toDo.setCollaborators(toDoDto.getCollaborators().stream().map(user -> UserTransformer.convertToEntity(user, user.getRole())).collect(Collectors.toList()));
        toDo.setCreatedAt(toDoDto.getCreatedAt());

        return toDo;
    }

    public static ToDo convertToEntity(ToDoRequestDto toDoRequestDto, User owner) {
        ToDo toDo = new ToDo();
        toDo.setTitle(toDoRequestDto.getTitle());
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setOwner(owner);
        return toDo;
    }

    private static Task convertTaskDtoToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        return task;
    }
}
