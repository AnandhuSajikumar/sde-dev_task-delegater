package com.anandhu.sde_dev.repository;

import com.anandhu.sde_dev.common.TaskStatus;
import com.anandhu.sde_dev.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findTaskByStatus(TaskStatus status, Pageable pageable);
    Page<Task> findByEngineerId(Long engineerId, Pageable pageable);
    Page<Task> findByEngineerIsNull(Pageable pageable);
    Page<Task> findByEngineerIsNotNull(Pageable pageable);
    Page<Task> findByEngineerUserEmail(String email, Pageable pageable);
}
