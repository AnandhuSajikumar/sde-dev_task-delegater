package com.anandhu.sde_dev.repository;

import com.anandhu.sde_dev.model.Engineer;
import com.anandhu.sde_dev.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineerRepository extends JpaRepository<Engineer,Long> {
}

