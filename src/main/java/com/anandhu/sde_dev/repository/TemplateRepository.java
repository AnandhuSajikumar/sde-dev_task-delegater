package com.anandhu.sde_dev.repository;

import com.anandhu.sde_dev.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Optional<Template> findByTemplateKey(String templateKey);
}
