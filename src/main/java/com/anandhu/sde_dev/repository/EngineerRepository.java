package com.anandhu.sde_dev.repository;

import com.anandhu.sde_dev.model.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineerRepository extends JpaRepository<Engineer,Long> {
}
