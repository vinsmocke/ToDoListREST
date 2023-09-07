package com.softserve.itacademy.todolist.dto.todo;

import com.softserve.itacademy.todolist.dto.task.TaskDto;
import com.softserve.itacademy.todolist.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ToDoDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private UserDto owner;
    private List<TaskDto> tasks;
    private List<UserDto> collaborators;

    public ToDoDto(Long id, @NotBlank(message = "The 'title' cannot be empty") String title, LocalDateTime createdAt, UserDto owner, List<TaskDto> tasks, List<UserDto> collaborators) {
        this.id = id;
        this.title = title;
        this.createdAt = LocalDateTime.now();//createdAt;
        this.owner = owner;
        this.tasks = tasks;
        this.collaborators = collaborators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDoDto toDoDto)) return false;

        if (!Objects.equals(id, toDoDto.id)) return false;
        if (!Objects.equals(title, toDoDto.title)) return false;
        if (!Objects.equals(createdAt, toDoDto.createdAt)) return false;
        if (!Objects.equals(tasks, toDoDto.tasks)) return false;
        return Objects.equals(collaborators, toDoDto.collaborators);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (collaborators != null ? collaborators.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ToDoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", tasks=" + tasks +
                ", collaborators=" + collaborators +
                '}';
    }
}