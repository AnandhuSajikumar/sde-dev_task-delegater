package com.anandhu.sde_dev.engineer;

public class EngineerMapper {

    public static Engineer toEntity (EngineerRequest request){
        return new Engineer(
                request.getName(),
                request.getTechStack(),
                request.getGender()
        );
    }


    public static EngineerResponse toResponse(Engineer engineer) {
        return new EngineerResponse(
                engineer.getId(),
                engineer.getName(),
                engineer.getTechStack()
        );
    }
}
