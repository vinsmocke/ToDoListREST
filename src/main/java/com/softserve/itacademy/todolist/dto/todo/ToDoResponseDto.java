package com.softserve.itacademy.todolist.dto.todo;

import com.softserve.itacademy.todolist.model.ToDo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ToDoResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private List<String> tasks;
    private List<String> collaborators;


    public ToDoResponseDto() {
    }

    public ToDoResponseDto(ToDo toDo) {
        id = toDo.getId();
        title = toDo.getTitle();
        createdAt = toDo.getCreatedAt();
        tasks = toDo.getTasks().stream().map(task -> task.getName()).collect(Collectors.toList());
        collaborators = toDo.getCollaborators().stream().map(collaborator -> collaborator.getUsername()).collect(Collectors.toList());
    }
}
