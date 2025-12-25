package com.anandhu.sde_dev.engineer;

import com.anandhu.sde_dev.common.Gender;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineerService {
    private final EngineerRepository engineerRepository;

    public EngineerService(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }
//GetALL
    public List<EngineerResponse> getAllEngineers(){
        return engineerRepository.findAll()
                .stream()
                .map(EngineerMapper::toResponse)
                .toList();
    }
//GetById
    public Engineer getEngineerById(Long id){
       return engineerRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Engineer not found with id: " + id));
    }
//DeleteById
    public void deleteEngineerById(Long id){
        if (!engineerRepository.existsById(id)) {
            throw new IllegalStateException("Engineer with id " + id + " does not exist");
        }
        engineerRepository.deleteById(id);
    }

//Put
    public Engineer createEngineer(String name, String techStack, Gender gender){
        Engineer engineer = new Engineer(name,techStack,gender);
        return engineerRepository.save(engineer);
//        Engineer engineer = EngineerMapper.toEntity(request);
//        Engineer saved = engineerRepository.save(engineer);
//        return EngineerMapper.toResponse(saved);

    }
}
