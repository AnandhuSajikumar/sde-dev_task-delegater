package com.anandhu.sde_dev.repository;

import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EngineerRepository extends JpaRepository<Engineer,Long> {
    Optional<Engineer> findByUserEmail(String userEmail);
}

