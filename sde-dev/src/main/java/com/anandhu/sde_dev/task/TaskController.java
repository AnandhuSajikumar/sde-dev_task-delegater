package com.anandhu.sde_dev.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PutMapping("/{taskId}/assign/{engineerId}")
    public TaskResponse assignTaskToEnigneer(@PathVariable Long taskId, @PathVariable Long engieerId){
        Task task = taskService.assignTaskToEngineer(taskId,engieerId);
        return TaskMapper.toResponse(task);
    }
    @PostMapping
    public TaskResponse createNewTask(@RequestBody TaskRequest request){
        Task task = taskService.createTask(
                request.getTitle(),
                request.getTaskStatus()
        );
        return TaskMapper.toResponse(task);
    }

}
