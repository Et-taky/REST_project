package com.project1.project1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import java.util.List;
import java.util.UUID;

public class PostCreateDto {

    @Size(min = 6, max = 1000,message = "{post.text.size}")
    @NotNull(message = "{post.notnull.text}")
    private String text;

    @URL(message = "{error.image.url}")
    private String image;

    private int likes;

    private List<String> tags;
    @NotNull(message = "{post.notnull.owner}")
    private UUID owner;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public UUID getOwner() {
        return owner;
    }       
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    
}
