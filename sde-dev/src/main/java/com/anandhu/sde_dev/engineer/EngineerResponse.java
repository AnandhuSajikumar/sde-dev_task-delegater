package com.anandhu.sde_dev.engineer;

public class EngineerResponse {
    private Long id;
    private String name;
    private String techStack;

    public EngineerResponse(Long id, String name, String techStack) {
        this.id = id;
        this.name = name;
        this.techStack = techStack;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTechStack() {
        return techStack;
    }
}
