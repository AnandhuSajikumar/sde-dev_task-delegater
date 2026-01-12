package com.anandhu.sde_dev.model;

import com.anandhu.sde_dev.common.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer engineer;

    public Task() {
    }

    private Task(String title, TaskStatus status) {
        this.title = title;
        this.status = status;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public Long getId() {
        return id;
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


    //Create a Task
    public static Task create(String title){
        return new Task(title, TaskStatus.OPEN);
    }

    //Update Task Title
    public void updateTitle(String title){
        if(this.title == null || title.isBlank()){
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }


//TaskStatus Transition Rules
    public void transitionTo(TaskStatus target){
        if(!isValidTransition(this.status, target)){
            throw new IllegalStateException(
                    "Invalid task status transition from " + this.status + " to " + target
            );
        }
        this.status = target;
    }

    private boolean isValidTransition(TaskStatus current, TaskStatus target){
        return switch (current) {
            case OPEN -> target == TaskStatus.IN_PROGRESS;
            case IN_PROGRESS -> target == TaskStatus.DONE || target == TaskStatus.OPEN;
            case DONE -> false;
        };
    }
//Task Assigning Rules
    public void assignTo(Engineer engineer){
        if(this.getStatus() == TaskStatus.DONE){
            throw new IllegalStateException("Cannot assign a completed task");
        }
        if(this.engineer != null  && this.engineer.equals(engineer)){
            return;
        }
        this.engineer = engineer;

        if(this.status == TaskStatus.OPEN){
            transitionTo(TaskStatus.IN_PROGRESS);
        }
    }

    public void unassign(){
        if(this.engineer == null){
            return;
        }
        if(this.status == TaskStatus.DONE){
            throw new IllegalStateException("Cannot unassign a completed task");
        }
        this.engineer = null;

        if(this.status != TaskStatus.OPEN){
            transitionTo(TaskStatus.OPEN);
        }
    }

    public void complete(){
        if(this.status != TaskStatus.IN_PROGRESS){
            throw new IllegalStateException("Only IN_PROGRESS tasks can be completed");
        }
        transitionTo(TaskStatus.DONE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}