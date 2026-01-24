package com.anandhu.sde_dev.dto.engineer;

import com.anandhu.sde_dev.common.Gender;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;


@Getter
public class EngineerRequest {
    private String name;
    private Gender gender;
    private String techStack;


    public EngineerRequest() {
    }



}