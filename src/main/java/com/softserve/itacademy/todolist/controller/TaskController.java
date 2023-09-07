package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.task.TaskRequestDto;
import com.softserve.itacademy.todolist.dto.task.TaskResponseDto;
import com.softserve.itacademy.todolist.dto.task.TaskTransformer;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.service.StateService;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import com.softserve.itacademy.todolist.service.impl.ApiUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users/{user_id}/todos/{todo_id}")
public class TaskController {
    private ToDoService toDoService;
    private TaskService taskService;
    private StateService stateService;

    @GetMapping("/all")
    @PreAuthorize("@check.isOwnerOrCollaboratorOfToDo(#todoId)")
    public List<TaskResponseDto> allTasks(@PathVariable(value = "user_id") String userId,
                                          @PathVariable(value = "todo_id") String todoId){
        List<Task> tasks = taskService.getByTodoId(Long.parseLong(todoId));
        return tasks.stream()
                .map(TaskResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping()
    @PreAuthorize("@check.isOwner(#userId)")
    public TaskResponseDto create(@PathVariable(value = "user_id") String userId,
                                  @PathVariable(value = "todo_id") String todoId,
                                  @RequestBody TaskRequestDto taskRequest) {
        Task task = taskService.create(TaskTransformer.convertToEntity(
                taskRequest,
                toDoService.readById(Long.parseLong(todoId)),
                stateService.getByName("NEW")
        ));
        return new TaskResponseDto(task);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{task_id}")
    @PreAuthorize("@check.isOwnerOrCollaboratorOfToDo(#todoId)")
    public TaskResponseDto update(@PathVariable(value = "user_id") String userId,
                                  @PathVariable(value = "todo_id") String todoId,
                                  @PathVariable(value = "task_id") String taskId,
                                  @RequestBody TaskRequestDto taskRequest) {

        Task task = taskService.update(TaskTransformer.convertToUpdatedEntity(
                Long.parseLong(taskId),
                taskRequest,
                toDoService.readById(Long.parseLong(todoId)),
                stateService.readById(taskRequest.getStateId())
        ));
        return new TaskResponseDto(task);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{task_id}")
    @PreAuthorize("@check.isOwner(#userId)")
    public List<TaskResponseDto> delete(@PathVariable(value = "user_id") long userId,
                                        @PathVariable(value = "todo_id") long todoId,
                                        @PathVariable(value = "task_id") long taskId) {

        taskService.delete(taskId);
        return taskService.getByTodoId(todoId).stream().map(TaskResponseDto::new).collect(Collectors.toList());
    }
}
