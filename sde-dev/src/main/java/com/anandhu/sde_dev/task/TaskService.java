package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.engineer.Engineer;
import com.anandhu.sde_dev.engineer.EngineerRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService{
    private final TaskRepository taskRepository;
    private final EngineerRepository engineerRepository;

    public TaskService(TaskRepository taskRepository, EngineerRepository engineerRepository) {
        this.taskRepository = taskRepository;
        this.engineerRepository = engineerRepository;
    }
//Add TASK
    public Task createTask (String title, TaskStatus taskStatus){
        Task task = new Task(title,taskStatus);
        return taskRepository.save(task);
    }

//assigning TASK
    public Task assignTaskToEngineer(Long taskId, Long engineerId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setEngineer(engineer);
        return taskRepository.save(task);
    }

}
