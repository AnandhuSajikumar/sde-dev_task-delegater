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
import org.apache.catalina.Engine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping("/me")
    @PreAuthorize("hasRole('USER')")
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<EngineerResponse> getAllEngineers(
            @PageableDefault(size = 5)
            Pageable pageable
    ){
        Page<Engineer> engineers = engineerService.getAllEngineers(pageable);
        return engineers.map(EngineerMapper::toResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EngineerResponse getEngineerById(@PathVariable Long id){
        Engineer engineer = engineerService.getEngineerById(id);
        return EngineerMapper.toResponse(engineer);
    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public EngineerResponse getMyEngineer(){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Engineer engineer = engineerService.getEngineerByUserEmail(email);
        return EngineerMapper.toResponse(engineer);
    }



    @PatchMapping("update/{id}")
    @PreAuthorize("hasRole('USER')")
    public EngineerResponse updateEngineer(
            @RequestBody @Valid EngineerUpdateRequest request
            ){
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Engineer engineer = engineerService.updateProfile(
                email,
                request.getName(),
                request.getAge(),
                request.getGender(),
                request.getTechStack()
        );
        return EngineerMapper.toResponse(engineer);
    }

    @GetMapping("me/tasks")
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEngineerById(@PathVariable Long id){
        engineerService.deleteEngineerById(id);
    }

}
