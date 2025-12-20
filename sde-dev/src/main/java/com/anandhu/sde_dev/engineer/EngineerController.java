package com.anandhu.sde_dev.engineer;

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

    @GetMapping("{id}")
    public EngineerResponse getEngineerById(@PathVariable Long id){
        return engineerService.getEngineerById(id);
    }

    @PostMapping
    public EngineerResponse createEngineer(@RequestBody EngineerRequest request){
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
