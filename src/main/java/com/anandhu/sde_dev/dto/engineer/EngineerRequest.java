package com.anandhu.sde_dev.dto.engineer;

import com.anandhu.sde_dev.common.Gender;

import java.math.BigDecimal;


public class EngineerRequest {
    private String name;
    private Integer age;
    private BigDecimal salary;
    private String techStack;
    private Gender gender;


    public EngineerRequest() {
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