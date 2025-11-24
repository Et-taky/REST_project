package com.project1.project1.controller.v1;

import com.project1.project1.dto.CommentCreateDto;
import com.project1.project1.dto.CommentDto;
import com.project1.project1.dto.ListResponseDto;
import com.project1.project1.services.CommentService;
import com.project1.project1.util.GenerateEtag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
@RequestMapping(path = "v1/comments",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CommentController {

    @Autowired
    CommentService commentService;

    // get list
    @GetMapping
    public ResponseEntity<ListResponseDto<EntityModel<CommentDto>>> getComments(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = { "publishDate" }, size = 10) Pageable pageable,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        ListResponseDto<CommentDto> comments = commentService.getAllComments(keyword,startDate, endDate, pageable);
        String eTag = GenerateEtag.generate(comments);

        if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }
        List<EntityModel<CommentDto>> resources = comments.getData().stream().map(CommentController::toModel).toList();
        Page<EntityModel<CommentDto>> modelPage =
                new PageImpl<>(resources, pageable, comments.getTotal());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(new ListResponseDto<>(modelPage));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<CommentDto>> getCommentById(
            @PathVariable UUID id,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        CommentDto commentDto = commentService.getCommentById(id);
        String eTag = GenerateEtag.generate(commentDto);

        if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }
        EntityModel<CommentDto> resource = toModel(commentDto);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(resource);
    }

    // get comments by post
    @GetMapping("/post/{id}")
    public ResponseEntity<ListResponseDto<EntityModel<CommentDto>>> getCommentsByPost(
            @RequestParam(required = false) String keyword,
            @PathVariable UUID id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = { "publishDate" }, size = 10) Pageable pageable,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        ListResponseDto<CommentDto> comments = commentService.getCommentsByPost(keyword,id, startDate, endDate, pageable);
        String eTag = GenerateEtag.generate(comments);

        if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }
        List<EntityModel<CommentDto>> resources = comments.getData().stream().map(CommentController::toModel).toList();
        Page<EntityModel<CommentDto>> modelPage =
                new PageImpl<>(resources, pageable, comments.getTotal());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(new ListResponseDto<>(modelPage));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ListResponseDto<EntityModel<CommentDto>>> getCommentsByUser(
            @RequestParam(required = false) String keyword,
            @PathVariable UUID id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(sort = { "publishDate" }, size = 10) Pageable pageable,
            @RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch) {
        ListResponseDto<CommentDto> comments = commentService.getCommentsByUser(keyword,id, startDate, endDate, pageable);
        String eTag = GenerateEtag.generate(comments);

        if (ifNoneMatch != null && ifNoneMatch.equals(eTag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(eTag)
                    .build();
        }
        List<EntityModel<CommentDto>> resources = comments.getData().stream().map(CommentController::toModel).toList();
        Page<EntityModel<CommentDto>> modelPage =
                new PageImpl<>(resources, pageable, comments.getTotal());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(new ListResponseDto<>(modelPage));
    }

    // create comment
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public CommentDto createComment(@RequestBody @Valid CommentCreateDto commentDto) {
        return commentService.createComment(commentDto);
    }

    // delete
    @DeleteMapping("/{id}")
    public UUID deleteComment(@PathVariable UUID id) {
        return commentService.deleteComment(id);
    }

    public static EntityModel<CommentDto> toModel(CommentDto commentDto) {
        return EntityModel.of(commentDto,
        linkTo(methodOn(CommentController.class).getCommentById(commentDto.getId(), null)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUserById(commentDto.getUser().getId(),null)).withRel("owner"),
                WebMvcLinkBuilder.linkTo(methodOn(PostController.class).getPostById(commentDto.getPostId(),null)).withRel("post")
        );}

}