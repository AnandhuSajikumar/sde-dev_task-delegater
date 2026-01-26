package com.anandhu.sde_dev;

import com.anandhu.sde_dev.common.Gender;
import com.anandhu.sde_dev.common.Role;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.model.Task;
import com.anandhu.sde_dev.model.User;
import com.anandhu.sde_dev.repository.EngineerRepository;
import com.anandhu.sde_dev.repository.TaskRepository;
import com.anandhu.sde_dev.repository.UserRepository;
import com.anandhu.sde_dev.service.TaskService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TaskServiceTAsk {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EngineerRepository engineerRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void engineer_cannot_complete_unassigned_task() {
        User user = userRepository.save(
                User.register("a", "b", "a@b.com", "pwd", Role.USER)
        );

        Engineer engineer = engineerRepository.save(
                Engineer.createFor(user, "A", "Java", Gender.MALE)
        );

        Task task = taskRepository.save(
                Task.create("Test task")
        );

        assertThrows(
                IllegalStateException.class,
                () -> taskService.completeTask(task.getId(), user.getEmail())
        );
    }
}

