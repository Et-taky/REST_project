
package com.project1.project1.controller.v1;
import com.project1.project1.dto.ListResponseDto;
import com.project1.project1.dto.UserFullDto;
import com.project1.project1.dto.UserPreviewDto;
import com.project1.project1.services.UserService;
import com.project1.project1.util.GenerateEtag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users",produces  = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },headers = "X-API-VERSION=1")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping
public ResponseEntity<ListResponseDto<EntityModel<UserPreviewDto>>> getUsers(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateOfBirth,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateOfBirth,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startRegisterDate,
        @RequestParam(required = false) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endRegisterDate,
        @RequestParam(required = false) String country,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String timezone,
        @PageableDefault(sort = "registerDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable,
        @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
) {
    ListResponseDto<UserPreviewDto> users = userService.getAllUsers(
            keyword,startDateOfBirth, endDateOfBirth, startRegisterDate, endRegisterDate,
            country, state, city, timezone, pageable
    );

    String eTag = GenerateEtag.generate(users);

    if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                             .eTag(eTag)
                             .build();
    }

        List<EntityModel<UserPreviewDto>> resources = users.getData().stream().map(UserController::toModelP).toList();
        Page<EntityModel<UserPreviewDto>> modelPage =
                new PageImpl<>(resources, pageable, users.getTotal());

    return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(new ListResponseDto<>(modelPage));
}

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user based on the provided ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(schema = @Schema(implementation = UserFullDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
@GetMapping("/{id}")
public ResponseEntity<EntityModel<UserFullDto>> getUserById(
        @PathVariable UUID id,
        @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch
) {
    UserFullDto user = userService.getUserById(id);
    String eTag = GenerateEtag.generate(user);

    if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                             .eTag(eTag)
                             .build();
    }
    EntityModel<UserFullDto> resource = toModelF(user);
    return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(resource);
}


    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
        public UserFullDto CreateUser(@RequestBody @Valid UserFullDto user){
       return userService.CreateUser(user);
    }

    @PutMapping(path = "/{id}",consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public UserFullDto updateUser(@PathVariable UUID id,@RequestBody @Valid UserFullDto user){
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    public UUID deleteUser(@PathVariable UUID id){
       return  userService.deleteUser(id);
    }

    public static EntityModel<UserPreviewDto> toModelP(UserPreviewDto userDto) {
        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).getUserById(userDto.getId(), null)).withSelfRel(),
                linkTo(methodOn(PostController.class).getPostsByUser(null,userDto.getId(), null,null,null,null,null,null,null)).withRel("posts"),
                linkTo(methodOn(CommentController.class).getCommentsByUser(null,userDto.getId(),null,null,null,null)).withRel("comments")
        );}

    public static EntityModel<UserFullDto> toModelF(UserFullDto userDto) {
        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).getUserById(userDto.getId(), null)).withSelfRel(),
                linkTo(methodOn(PostController.class).getPostsByUser(null,userDto.getId(), null,null,null,null,null,null,null)).withRel("user:posts"),
                linkTo(methodOn(CommentController.class).getCommentsByUser(null,userDto.getId(),null,null,null,null)).withRel("user:comments")
        );}

}