package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.dto.template.TemplateRequest;
import com.anandhu.sde_dev.dto.template.TemplateResponse;
import com.anandhu.sde_dev.exception.ResourceNotFoundException;
import com.anandhu.sde_dev.model.Template;
import com.anandhu.sde_dev.repository.TemplateRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @PostConstruct
    public void seedDefaultTemplates() {
        if (templateRepository.findByTemplateKey("TASK_ASSIGNED").isEmpty()) {
            Template template = new Template("TASK_ASSIGNED", "EMAIL", "Task assigned to engineer");
            templateRepository.save(template);
        }
    }

    public TemplateResponse createTemplate(TemplateRequest request) {
        Template template = new Template(request.getTemplateKey(), request.getChannel(), request.getLabel());
        Template saved = templateRepository.save(template);
        return new TemplateResponse(saved.getId(), saved.getTemplateKey(), saved.getChannel(), saved.getLabel());
    }

    public TemplateResponse getTemplate(String templateKey) {
        Template template = templateRepository.findByTemplateKey(templateKey)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found for key: " + templateKey));
        return new TemplateResponse(template.getId(), template.getTemplateKey(), template.getChannel(), template.getLabel());
    }
}
