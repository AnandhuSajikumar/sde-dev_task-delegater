package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.common.Gender;
import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.model.User;
import com.anandhu.sde_dev.repository.EngineerRepository;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import com.anandhu.sde_dev.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EngineerService {
    private final EngineerRepository engineerRepository;
    private final UserRepository userRepository;

    public EngineerService(EngineerRepository engineerRepository, UserRepository userRepository) {
        this.engineerRepository = engineerRepository;
        this.userRepository = userRepository;
    }

    //POST
    public Engineer createForUser(String email, String name, String techStack, Gender gender){
        log.debug("Registering engineer for email={}",email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Register failed, User not found={}",email);
                    return new ResourceNotFoundException("User not found");
                });

        Engineer engineer = Engineer.createFor(user, name, techStack, gender);
        log.info("Engineer creation success email={}",email);
        return engineerRepository.save(engineer);

    }

    @Transactional
    //Update PROFILE
    public Engineer updateProfile(
            String email,
            String name,
            Integer age,
            Gender gender,
            String techStack
    ){
        Engineer engineer = engineerRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer Not Found"));

        if(name != null){
            engineer.updateName(name);
        }

        if(age != null){
            engineer.updateAge(age);
        }

        if(gender != null){
            engineer.updateGender(gender);
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
    public void deleteEngineerById(Long id) {
        Engineer engineer = engineerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));
        engineerRepository.delete(engineer);
    }

    public Engineer getEngineerByUserEmail(String email) {
        return engineerRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Engineer not found"));

    }
}
