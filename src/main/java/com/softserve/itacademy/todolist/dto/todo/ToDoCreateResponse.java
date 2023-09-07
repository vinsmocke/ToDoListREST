package com.softserve.itacademy.todolist.dto.todo;

import com.softserve.itacademy.todolist.model.ToDo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ToDoCreateResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public ToDoCreateResponse(ToDo toDo) {
        id = toDo.getId();
        title = toDo.getTitle();
        createdAt = toDo.getCreatedAt();
    }
}
