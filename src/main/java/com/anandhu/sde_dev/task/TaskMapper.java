package com.anandhu.sde_dev.task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task){
        Long engineerId = task.getEngineer() != null ? task.getEngineer().getId() : null;

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                engineerId
        );
    }
}
