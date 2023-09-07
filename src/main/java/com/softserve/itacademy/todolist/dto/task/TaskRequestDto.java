package com.softserve.itacademy.todolist.dto.task;

public class TaskRequestDto {
    private String name;
    private String priority;
    private long todoId;
    private long stateId;

    public TaskRequestDto(String name) {
        this.name = name;
    }

    public TaskRequestDto(String name, String priority, long todoId, long stateId) {
        this.name = name;
        this.priority = priority;
        this.todoId = todoId;
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }
}