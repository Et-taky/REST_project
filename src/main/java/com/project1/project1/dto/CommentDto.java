package com.project1.project1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class CommentDto {

    private UUID id;
    private UserPreviewDto user;
    private UUID postId;
    private String message;
    private LocalDate publishDate;


    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UserPreviewDto getUser() {
        return user;
    }

    public void setUser(UserPreviewDto user) {
        this.user = user;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    public LocalDate getPublishDate() {
        return publishDate;
    }

}