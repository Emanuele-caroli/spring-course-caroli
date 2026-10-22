package com.example.spring_course.service.mapper;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.dto.UserRequestDto;
import com.example.spring_course.service.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperUserDto {
    @Mapping(
            target = "indirizzo",
            expression = "java(mapIndirizzo(userRequest.getCitta(), userRequest.getNazione()))")
    public abstract UserRequestDto toRequestDto(UserRequest userRequest);

    @Mapping(source = "message", target = "messaggio")
    @Mapping(source = "id", target = "idGenerato")
    public abstract UserResponse toResponse(UserResponseDto userResponseDto);

    public String mapIndirizzo(String citta, String nazione) {
        return citta + " - " + nazione;
    }
}
