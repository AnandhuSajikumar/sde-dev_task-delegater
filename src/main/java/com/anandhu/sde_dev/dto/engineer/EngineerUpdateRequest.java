package com.anandhu.sde_dev.dto.engineer;

import com.anandhu.sde_dev.common.Gender;
import lombok.Getter;

@Getter
public class EngineerUpdateRequest {
    private String name;
    private Integer age;
    private Gender gender;
    private String techStack;


    public EngineerUpdateRequest() {
    }


}
