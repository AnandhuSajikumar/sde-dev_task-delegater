package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {

    @NotBlank
    private String title;
    @NotNull
    private TaskStatus Status;

    public TaskRequest(String title, TaskStatus Status) {
        this.title = title;
        this.Status = Status;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return Status;
    }
}
