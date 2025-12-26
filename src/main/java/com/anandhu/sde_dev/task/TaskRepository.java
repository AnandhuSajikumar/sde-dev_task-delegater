package com.anandhu.sde_dev.task;

import com.anandhu.sde_dev.common.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTaskByStatus(TaskStatus status);

}
