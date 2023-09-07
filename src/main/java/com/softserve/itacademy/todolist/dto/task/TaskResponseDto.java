package com.softserve.itacademy.todolist.dto.task;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.model.Task;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskResponseDto {

    private long id;
    private String name;
    private String priority;
    private String todo;
    private String state;

    public TaskResponseDto(Task task) {
        id = task.getId();
        name = task.getName();
        priority = task.getPriority().toString();
        todo = task.getTodo().getTitle();
        state = task.getState().getName();
    }
}
