package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.dto.task.TaskRequest;
import com.anandhu.sde_dev.dto.task.TaskResponse;
import com.anandhu.sde_dev.mapper.EngineerMapper;
import com.anandhu.sde_dev.dto.engineer.EngineerResponse;
import com.anandhu.sde_dev.mapper.TaskMapper;
import com.anandhu.sde_dev.model.Task;
import com.anandhu.sde_dev.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//Create a task
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TaskResponse createNewTask(@Valid @RequestBody TaskRequest request){
        Task task = taskService.createTask(
                request.getTitle()
        );
        return TaskMapper.toResponse(task);
    }

//assignEngineer

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{taskId}/assign/{engineerId}")
    public TaskResponse assignTaskToEnigneer(@PathVariable Long taskId, @PathVariable Long engineerId){
        Task task = taskService.assignTaskToEngineer(taskId,engineerId);
        return TaskMapper.toResponse(task);
    }
    //Unassign Task
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{taskId}/unassign")
    public TaskResponse unAssignTask(@PathVariable Long taskId){
        return TaskMapper.toResponse(taskService.unAssignTask(taskId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<TaskResponse> getAllTask(@PageableDefault(size = 5) Pageable pageable){
        return taskService.getAllTask(pageable)
                .map(TaskMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}/tasks")
    public Page<TaskResponse> getAllTaskByEngineer(
            @PathVariable Long id,
            @PageableDefault(size = 5)
            Pageable pageable){
        return taskService.tasksOfEngineer(id, pageable)
                .map(TaskMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public Page<TaskResponse> getTaskByStatus(
            @RequestParam TaskStatus status,
            @PageableDefault(size = 5)
            Pageable pageable) {

        return taskService.getTaskByStatus(status, pageable)
                .map(TaskMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return TaskMapper.toResponse(task);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/engineer")
    public EngineerResponse getEngineerofTask(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return EngineerMapper.toResponse(task.getEngineer());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/unassigned")
    public Page<TaskResponse> getAlUnassignedTasks(
            @PageableDefault(size = 5)
            Pageable pageable
    ){
        return taskService.findTaskToBeAssigned(pageable)
                .map(TaskMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/assigned")
    public Page<TaskResponse> getAllAssignedTasks(
            @PageableDefault(size = 5)
            Pageable pageable
    ){
        return taskService.findAllAssignedTasks(pageable)
                .map(TaskMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TaskResponse UpdateTaskById(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request)
    {

        Task task = taskService.UpdateTask(
                id,
                request.getTitle()
        );
        return TaskMapper.toResponse(task);

    }

    @PostMapping("/{taskId}/complete")
    @PreAuthorize("hasRole('USER')")
    public TaskResponse completeTask(@PathVariable Long taskId){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Task task = taskService.completeTask(taskId,email);
        return TaskMapper.toResponse((task));

    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }


}
