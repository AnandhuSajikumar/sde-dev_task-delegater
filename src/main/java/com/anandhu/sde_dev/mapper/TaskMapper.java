package com.anandhu.sde_dev.mapper;

import com.anandhu.sde_dev.dto.task.TaskResponse;
import com.anandhu.sde_dev.model.Task;

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
