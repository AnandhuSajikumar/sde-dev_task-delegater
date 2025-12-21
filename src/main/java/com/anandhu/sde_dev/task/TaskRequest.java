package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

public class TaskRequest {

    @NotBlank
    private String title;
    @NotNull
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
