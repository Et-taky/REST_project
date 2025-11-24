package com.project1.project1.dto;

import com.project1.project1.enums.Title;
import com.project1.project1.model.Location;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import java.util.UUID;


public class UserFullDto {

    private UUID id;
    @NotNull(message = "{user.notnull.title}")
    private Title title;

    @NotNull(message = "{user.notnull.firstname}")
    @Size(min=2,max = 50,message = "{user.firstname.size")
    private String firstName;

    @NotNull(message = "{user.notnull.lastname}")
    @Size(min=2,max = 50,message = "{user.lastname.size")
    private String lastName;

    @NotNull(message = "{user.notnull.email}")
    @Email(message = "{user.notvalid.email}")
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate registerDate;
    private String phone;

    @URL(message = "{error.image.url}")
    private String picture;

    private Location location;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}