package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.engineer.Engineer;
import com.anandhu.sde_dev.engineer.EngineerRepository;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EngineerRepository engineerRepository;

    public TaskService(TaskRepository taskRepository, EngineerRepository engineerRepository) {
        this.taskRepository = taskRepository;
        this.engineerRepository = engineerRepository;
    }

    //Create TASK
    public Task createTask(String title, TaskStatus taskStatus) {
        Task task = new Task(title, taskStatus);
        return taskRepository.save(task);
    }

    //UpdateTASK
    public Task UpdateTask(Long id, String title, TaskStatus taskStatus){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setTitle(title);
        task.setStatus(taskStatus);
        return taskRepository.save(task);
    }

    //GetAllTask
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    //GetTaskById
    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
    //DeleteTASK
    public void  deleteTaskById(Long id){
        if (!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);

    }

    //assigning TASK
    public Task assignTaskToEngineer(Long taskId, Long engineerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setEngineer(engineer);
        return taskRepository.save(task);
    }
}

