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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//Create a task
    @PostMapping
    public TaskResponse createNewTask(@Valid @RequestBody TaskRequest request){
        Task task = taskService.createTask(
                request.getTitle(),
                request.getStatus()
        );
        return TaskMapper.toResponse(task);
    }

//assignEngineer
    @PutMapping("/{taskId}/assign/{engineerId}")
    public TaskResponse assignTaskToEnigneer(@PathVariable Long taskId, @PathVariable Long engineerId){
        Task task = taskService.assignTaskToEngineer(taskId,engineerId);
        return TaskMapper.toResponse(task);
    }
    //Unassign Task
    @PutMapping("/{taskId}/unassign")
    public TaskResponse unAssignTask(@PathVariable Long taskId){
        return TaskMapper.toResponse(taskService.unAssignTask(taskId));
    }


    @GetMapping
    public Page<TaskResponse> getAllTask(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Pageable pageable){
        return taskService.getAllTask(PageRequest.of(page,size))
                .map(TaskMapper::toResponse);
    }
    @GetMapping("/filter")
    public Page<TaskResponse> getTaskByStatus(
            @RequestParam TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Pageable pageable) {

        return taskService.getTaskByStatus(status, pageable)
                .map(TaskMapper::toResponse);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return TaskMapper.toResponse(task);
    }
    @GetMapping("/{id}/engineer")
    public EngineerResponse getEngineerofTask(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return EngineerMapper.toResponse(task.getEngineer());
    }
    @GetMapping("/unassigned")
    public Page<TaskResponse> getAlUnassignedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Pageable pageable
    ){
        return taskService.findTaskToBeAssigned(PageRequest.of(page,size))
                .map(TaskMapper::toResponse);
    }

    @GetMapping("/assigned")
    public Page<TaskResponse> getAllAssignedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Pageable pageable
    ){
        return taskService.findAllAssignedTasks(PageRequest.of(page,size))
                .map(TaskMapper::toResponse);
    }


    @PutMapping("/{id}")
    public TaskResponse UpdateTaskById(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request)
    {

        Task task = taskService.UpdateTask(
                id,
                request.getTitle(),
                request.getStatus()
        );
        return TaskMapper.toResponse(task);

    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }


}
