package com.project1.project1.dto.mappers;


import com.project1.project1.dto.UserFullDto;
import com.project1.project1.dto.UserPreviewDto;
import com.project1.project1.model.User;
import org.mapstruct.*;
import org.springframework.web.bind.annotation.GetMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserPreviewDto toPreviewDto(User user);

    UserFullDto toFullDto(User user);
    @Mapping(target = "registerDate", ignore = true)
    User toEntity(UserFullDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "registerDate", ignore = true)
    void updateUserFromDto(UserFullDto dto, @MappingTarget User user);
}
