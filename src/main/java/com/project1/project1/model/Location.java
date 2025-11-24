package com.project1.project1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Location {
    @Size(min=5, max=100,message = "Steet should be between 5 and 100 charatcter")
    private String street;
    @Size(min=2, max=30,message = "City should be between 5 and 30 charatcter")
    private String city;
    @Size(min=2, max=30,message = "Steet should be between 5 and 30 charatcter")
    private String state;
    @Size(min=2, max=30,message = "Steet should be between 5 and 30 charatcter")
    private String country;
    @Pattern(regexp = "^[+-](?:2[0-3]|[01][0-9]):[0-5][0-9]$",
            message = "Timezone must be in the format +HH:MM or -HH:MM")
    private String timezone;


    public Location() {}

    public  Location(String street, String city, String state, String country, String timezone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.timezone = timezone;
    }


    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

}
