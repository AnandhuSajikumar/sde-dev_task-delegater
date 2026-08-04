package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.dto.template.TemplateRequest;
import com.anandhu.sde_dev.dto.template.TemplateResponse;
import com.anandhu.sde_dev.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateResponse createTemplate(@Valid @RequestBody TemplateRequest request) {
        return templateService.createTemplate(request);
    }

    @GetMapping("/{templateKey}")
    public TemplateResponse getTemplate(@PathVariable String templateKey) {
        return templateService.getTemplate(templateKey);
    }
}
