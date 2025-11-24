package com.project1.project1.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String message;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate publishDate=LocalDate.now();



public Comment(){}
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    public void setPublishDate(LocalDate publishDate) {
    this.publishDate = publishDate;
    }
    public LocalDate getPublishDate() {
    return publishDate;
    }
}