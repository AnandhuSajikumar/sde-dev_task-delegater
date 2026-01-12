package com.anandhu.sde_dev.dto.engineer;

import com.anandhu.sde_dev.common.Gender;

import java.math.BigDecimal;

public class EngineerUpdateRequest {
    private String name;
    private Integer age;
    private BigDecimal salary;
    private Gender gender;
    private String techStack;


    public EngineerUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getTechStack() {
        return techStack;
    }

    public Gender getGender() {
        return gender;
    }



}
