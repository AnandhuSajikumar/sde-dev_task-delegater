package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.dto.engineer.EngineerRequest;
import com.anandhu.sde_dev.dto.engineer.EngineerResponse;
import com.anandhu.sde_dev.mapper.EngineerMapper;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.service.EngineerService;
import com.anandhu.sde_dev.mapper.TaskMapper;
import com.anandhu.sde_dev.dto.task.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/engineers")
public class EngineerController {
    private final EngineerService engineerService;

    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    @GetMapping
    public Page<EngineerResponse> getAllEngineers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Pageable pageable
    ){
        Page<Engineer> engineers = engineerService.getAllEngineers(PageRequest.of(page,size));
        return engineers.map(EngineerMapper::toResponse);
    }

    @GetMapping("/{id}")
    public EngineerResponse getEngineerById(@PathVariable Long id){
        Engineer engineer = engineerService.getEngineerById(id);
        return EngineerMapper.toResponse(engineer);
    }
    @GetMapping("/{id}/tasks")
    public List<TaskResponse> getAllTasksofEngineer(@PathVariable Long id){
        Engineer engineer = engineerService.getEngineerById(id);
        return engineer.getTasks()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }


    @PostMapping
    public EngineerResponse createEngineer(@RequestBody @Valid EngineerRequest request){
        Engineer engineer = engineerService.createEngineer(
                request.getName(),
                request.getTechStack(),
                request.getGender()
        );
        return EngineerMapper.toResponse(engineer);
    }
    @PatchMapping("update/{id}")
    public EngineerResponse updateEngineer(
            @PathVariable Long id,
            @RequestBody @Valid EngineerRequest request
            ){
        Engineer engineer = engineerService.updateProfile(
                id,
                request.getAge(),
                request.getSalary(),
                request.getTechStack()
        );
        return EngineerMapper.toResponse(engineer);
    }

    @DeleteMapping("{id}")
    public void deleteEngineerById(@PathVariable Long id){
        engineerService.deleteEngineerById(id);
    }

}
