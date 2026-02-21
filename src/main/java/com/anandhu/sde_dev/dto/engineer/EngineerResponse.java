package com.anandhu.sde_dev.dto.engineer;

import lombok.Getter;

@Getter
public class EngineerResponse {
    private Long id;
    private String name;
    private String techStack;
    private com.anandhu.sde_dev.common.Gender gender;

    public EngineerResponse(Long id, String name, String techStack, com.anandhu.sde_dev.common.Gender gender) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
        this.gender = gender;
    }

}
