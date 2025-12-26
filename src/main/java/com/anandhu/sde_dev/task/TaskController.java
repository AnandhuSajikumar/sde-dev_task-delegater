package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.engineer.EngineerMapper;
import com.anandhu.sde_dev.engineer.EngineerResponse;
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
//assignEngineer
    @PutMapping("/{taskId}/assign/{engineerId}")
    public TaskResponse assignTaskToEnigneer(@PathVariable Long taskId, @PathVariable Long engineerId){
        Task task = taskService.assignTaskToEngineer(taskId,engineerId);
        return TaskMapper.toResponse(task);
    }
    @GetMapping
    public Page<TaskResponse> getAllTask(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Pageable pageable){
        Page<Task> tasks = taskService.getAllTask(PageRequest.of(page,size));
        return tasks.map(TaskMapper::toResponse);
    }
    @GetMapping("/filter")
    public List<TaskResponse> getTaskByStatus(@RequestParam TaskStatus status){
        return taskService.getTaskByStatus(status)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
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

    @PostMapping
    public TaskResponse createNewTask(@Valid @RequestBody TaskRequest request){
        Task task = taskService.createTask(
                request.getTitle(),
                request.getStatus()
        );
        return TaskMapper.toResponse(task);
    }

}
