package com.project1.project1.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Size;

public class PostFullDto {

    private UUID id;
    private String text;
    private String image;
    private int likes;
    private List<String> tags;
    private String link;
    private LocalDate publishDate;
    private UserPreviewDto owner;

    
    public UUID getId() {
        return id;
    }   
    public void setId(UUID id) {
        this.id = id;
    }

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
    public LocalDate getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    public UserPreviewDto getOwner() {
        return owner;
    }
    public void setOwner(UserPreviewDto owner) {
        this.owner = owner;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link ) {
        this.link = link;
    }

}
