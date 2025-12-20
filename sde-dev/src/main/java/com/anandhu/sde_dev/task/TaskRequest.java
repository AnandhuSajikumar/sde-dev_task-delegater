package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;

public class TaskRequest {
    private String title;
    private TaskStatus taskStatus;

    public TaskRequest(String title, TaskStatus taskStatus) {
        this.title = title;
        this.taskStatus = taskStatus;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
}
