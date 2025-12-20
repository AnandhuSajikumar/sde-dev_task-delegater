package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.engineer.Engineer;
import jakarta.persistence.*;
import java.util.Objects;

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer engineer;

    public Task() {
    }

    public Task(String title, TaskStatus status) {
        this.title = title;
        this.status = status;
    }
    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}