package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.dto.engineer.EngineerRequest;
import com.anandhu.sde_dev.dto.engineer.EngineerResponse;
import com.anandhu.sde_dev.dto.engineer.EngineerUpdateRequest;
import com.anandhu.sde_dev.mapper.EngineerMapper;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.service.EngineerService;
import com.anandhu.sde_dev.mapper.TaskMapper;
import com.anandhu.sde_dev.dto.task.TaskResponse;
import com.anandhu.sde_dev.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/engineers")
public class EngineerController {
    private final EngineerService engineerService;
    private final TaskService taskService;

    public EngineerController(EngineerService engineerService, TaskService taskService) {
        this.engineerService = engineerService;
        this.taskService = taskService;
    }


    @PostMapping
    public EngineerResponse createEngineerProfile(@RequestBody @Valid EngineerRequest request){

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Engineer engineer = engineerService.createForUser(
                email,
                request.getName(),
                request.getTechStack(),
                request.getGender()
        );
        return EngineerMapper.toResponse(engineer);
    }

    @GetMapping
    public Page<EngineerResponse> getAllEngineers(
            @PageableDefault(size = 5)
            Pageable pageable
    ){
        Page<Engineer> engineers = engineerService.getAllEngineers(pageable);
        return engineers.map(EngineerMapper::toResponse);
    }

    @GetMapping("/{id}")
    public EngineerResponse getEngineerById(@PathVariable Long id){
        Engineer engineer = engineerService.getEngineerById(id);
        return EngineerMapper.toResponse(engineer);
    }

    @PatchMapping("update/{id}")
    public EngineerResponse updateEngineer(
            @PathVariable Long id,
            @RequestBody @Valid EngineerUpdateRequest request
            ){
        Engineer engineer = engineerService.updateProfile(
                id,
                request.getName(),
                request.getAge(),
                request.getSalary(),
                request.getGender(),
                request.getTechStack()
        );
        return EngineerMapper.toResponse(engineer);
    }

    public Page<TaskResponse> getMyTasks(
            @PageableDefault(size = 5)
            Pageable pageable){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return taskService.findTasksByEngineerEmail(email,pageable)
                .map(TaskMapper::toResponse);
    }



    @DeleteMapping("{id}")
    public void deleteEngineerById(@PathVariable Long id){
        engineerService.deleteEngineerById(id);
    }

}
