package com.anandhu.sde_dev.engineer;

import com.anandhu.sde_dev.task.TaskMapper;
import com.anandhu.sde_dev.task.TaskRequest;
import com.anandhu.sde_dev.task.TaskResponse;
import jakarta.validation.Valid;
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
    public List<EngineerResponse> getEngineers(){
        return engineerService.getAllEngineers();
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

    @DeleteMapping("{id}")
    public void deleteEngineerById(@PathVariable Long id){
        engineerService.deleteEngineerById(id);
    }

}
