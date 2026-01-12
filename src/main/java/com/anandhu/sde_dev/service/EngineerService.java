package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.common.Gender;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.repository.EngineerRepository;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EngineerService {
    private final EngineerRepository engineerRepository;

    public EngineerService(EngineerRepository engineerRepository) {
        this.engineerRepository = engineerRepository;
    }

    //POST
    public Engineer createEngineer(String name, String techStack, Gender gender){
        Engineer engineer = Engineer.create(name, techStack, gender);
        return engineerRepository.save(engineer);

    }

    @Transactional
    //Update PROFILE
    public Engineer updateProfile(
            Long engineerId,
            Integer age,
            BigDecimal salary,
            String techStack
    ){
        Engineer engineer = engineerRepository.findById(engineerId)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer Not Found"));

        if(age != null){
            engineer.updateAge(age);
        }
        if(salary != null){
            engineer.updateSalary(salary);
        }
        if(techStack != null){
            engineer.updateTechStack(techStack);
        }
        return engineerRepository.save(engineer);

    }


//GetALL
    public Page<Engineer> getAllEngineers(Pageable pageable){
        return engineerRepository.findAll(pageable);
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


}
