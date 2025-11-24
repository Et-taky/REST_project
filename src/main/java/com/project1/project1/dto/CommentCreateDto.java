package com.project1.project1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CommentCreateDto {
    @Size(min = 6, max = 1000,message = "{comment.size}")
    @NotNull(message = "{comment.notnull.message}")
    private String message;

    @NotNull(message = "{comment.notnull.owner}")
    private UUID owner;

    @NotNull(message = "{comment.notnull.post}")
    private UUID post;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getOwner() {
        return owner;
    }
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public UUID getPost() {
        return post;
    }
    public void setPost(UUID post) {
        this.post = post;
    }

    
}
