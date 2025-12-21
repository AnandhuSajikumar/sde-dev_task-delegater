package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public List<TaskResponse> getAllTask(){
        return taskService.getAllTask()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        Task task = taskService.getTaskById(id);
        return TaskMapper.toResponse(task);
    }

    @PutMapping("/{id")
    public TaskResponse UpdateTaskById(
            @PathVariable Long id,
            @RequestBody TaskRequest request)
    {

        Task task = taskService.UpdateTask(
                id,
                request.getTitle(),
                request.getTaskStatus()
        );
        return TaskMapper.toResponse(task);

    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        taskService.deleteTaskById(id);
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
