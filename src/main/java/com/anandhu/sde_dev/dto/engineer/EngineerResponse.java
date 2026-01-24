package com.anandhu.sde_dev.dto.engineer;

import lombok.Getter;

@Getter
public class EngineerResponse {
    private Long id;
    private String name;
    private String techStack;

    public EngineerResponse(Long id, String name, String techStack) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
    }

}
