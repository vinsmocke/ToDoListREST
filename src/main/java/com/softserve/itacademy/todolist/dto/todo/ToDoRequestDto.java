package com.softserve.itacademy.todolist.dto.todo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ToDoRequestDto {
    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    public ToDoRequestDto(String title) {
        this.title = title;
    }

    public ToDoRequestDto() {
    }

}