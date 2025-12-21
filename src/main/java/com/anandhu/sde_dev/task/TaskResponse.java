package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import org.apache.catalina.Engine;

public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus taskStatus;
    private Long engineerId;

    public TaskResponse(Long id, String title, TaskStatus taskStatus, Long engineerId) {
        this.id = id;
        this.title = title;
        this.taskStatus = taskStatus;
        this.engineerId = engineerId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Long getEngineerId() {
        return engineerId;
    }
}
