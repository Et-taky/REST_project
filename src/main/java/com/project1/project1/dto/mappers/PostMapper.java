package com.project1.project1.dto.mappers;

import com.project1.project1.dto.PostCreateDto;
import com.project1.project1.dto.PostFullDto;
import com.project1.project1.dto.PostPreviewDto;
import com.project1.project1.model.Post;
import com.project1.project1.model.User;

import java.util.List;
import java.util.UUID;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
// import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",uses=UserMapper.class)
public interface PostMapper {

    // Création : PostDto -> Post entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
    @Mapping(target = "link", ignore = true)
    @Mapping(source = "owner", target = "owner" , qualifiedByName = "mapOwnerIdToUser")
    Post toEntity(PostCreateDto dto);
    @Named("mapOwnerIdToUser")
    default User mapOwnerIdToUser(UUID ownerId) {
        User user = new User();
        user.setId(ownerId);
        return user;
    }

    // Preview : Post entity -> PostDto
    @Mapping(source = "owner", target = "owner") // MapStruct va utiliser toUserPreviewDto
    PostPreviewDto toPreviewDto(Post post);

    // Full return : Post entity -> PostResponseDto
    @Mapping(source = "owner", target = "owner") // MapStruct va utiliser toUserPreviewDto
    PostFullDto toFullDto(Post post);

    // Mapping liste
    List<PostPreviewDto> toDtoList(List<Post> posts);
    List<PostFullDto> toResponseDtoList(List<Post> posts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishDate", ignore = true)
     void updatePostDto(PostFullDto postFullDto,@MappingTarget Post post);
}
