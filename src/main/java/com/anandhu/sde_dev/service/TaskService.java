package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.repository.EngineerRepository;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import com.anandhu.sde_dev.model.Task;
import com.anandhu.sde_dev.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final EngineerRepository engineerRepository;

    public TaskService(TaskRepository taskRepository, EngineerRepository engineerRepository) {
        this.taskRepository = taskRepository;
        this.engineerRepository = engineerRepository;
    }

    //Create TASK
    public Task createTask(String title) {
        Task task = Task.create(title);
        return taskRepository.save(task);
    }
    //Read-GetAllTask
    public Page<Task> getAllTask(Pageable pageable){
        return taskRepository.findAll(pageable);
    }


    //UpdateTASK
    public Task UpdateTask(Long id, String title){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.updateTitle(title);
        return taskRepository.save(task);
    }

    //DeleteTASK
    public void  deleteTaskById(Long id){
        if (!taskRepository.existsById(id)){
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);

    }

    //GetTaskById
    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    //find Task By Status
    public Page<Task> getTaskByStatus(TaskStatus status, Pageable pageable){
        return taskRepository.findTaskByStatus(status, pageable);
    }
    //find tasks of an engineer by id
    public Page<Task> tasksOfEngineer(Long engineerId, Pageable pageable){
        return taskRepository.findByEngineerId(engineerId, pageable);
    }

    //find all unassigned tasks
    public Page<Task> findTaskToBeAssigned(Pageable pageable){
        return taskRepository.findByEngineerIsNull(pageable);
    }

    //find all Assigned tasks
    public Page<Task> findAllAssignedTasks(Pageable pageable){
        return taskRepository.findByEngineerIsNotNull(pageable);
    }
//Complete Task
    @Transactional
    public Task completeTask(Long taskId, Long engineerId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->  new ResourceNotFoundException("Task not found"));

        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));

        if(task.getEngineer() == null || !task.getEngineer().getId().equals(engineerId)){
            throw new IllegalStateException("Task not assigned to this engineer");
        }
        task.complete();

        return taskRepository.save(task);
    }


    //assigning TASK
    @Transactional
    public Task assignTaskToEngineer(Long taskId, Long engineerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));

        task.assignTo(engineer);

        return taskRepository.save(task);
    }

    //Unassign a Task
    @Transactional
    public Task unAssignTask(Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.unassign();

        return taskRepository.save(task);
    }

}

