package com.anandhu.sde_dev;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTEst {
    @Test
    void should_throw_when_task_not_in_progress(){
        Task task = Task.create("task");
        assertThrows(
                IllegalStateException.class,
                () -> task.complete()
        );
    }

    @Test
    void should_complete_task_when_in_progress(){
        Task task = Task.create("task");

        task.transitionTo(TaskStatus.IN_PROGRESS);
        task.complete();

        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    void invalid_TransitionTo(){
        Task task  =  Task.create("title");

        assertThrows(
                IllegalStateException.class,
                () -> task.transitionTo(TaskStatus.DONE)
        );
    }

    @Test
    void valid_transitionTo(){
        Task task = Task.create("title");

        task.transitionTo(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }
}
