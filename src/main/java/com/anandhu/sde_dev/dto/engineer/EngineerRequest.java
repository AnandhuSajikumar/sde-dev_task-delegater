package com.anandhu.sde_dev.dto.engineer;

import com.anandhu.sde_dev.common.Gender;

public class EngineerRequest {
    private String name;
    private String techStack;
    private Gender gender;


    public EngineerRequest() {
    }

    public String getName() {
        return name;
    }

    public String getTechStack() {
        return techStack;
    }

    public Gender getGender() {
        return gender;
    }

    public EngineerRequest(String name, String techStack, Gender gender) {
        this.name = name;
        this.techStack = techStack;
        this.gender = gender;
    }
}