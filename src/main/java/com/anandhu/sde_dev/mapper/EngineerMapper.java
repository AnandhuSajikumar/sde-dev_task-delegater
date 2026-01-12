package com.anandhu.sde_dev.mapper;

import com.anandhu.sde_dev.dto.engineer.EngineerRequest;
import com.anandhu.sde_dev.dto.engineer.EngineerResponse;
import com.anandhu.sde_dev.model.Engineer;

public class EngineerMapper {


    public static EngineerResponse toResponse(Engineer engineer) {
        return new EngineerResponse(
                engineer.getId(),
                engineer.getName(),
                engineer.getTechStack()
        );
    }
}
