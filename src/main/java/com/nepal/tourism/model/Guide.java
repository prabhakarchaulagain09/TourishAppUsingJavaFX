package com.nepal.tourism.model;

import java.io.Serializable;
import java.util.List;

public class Guide implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private List<String> languages;
    private int experienceYears;
    private String contact;
    private String specialization;
    private double rating;
    private boolean available;

    public Guide() {
        this.available = true;
        this.rating = 5.0;
    }

    public Guide(String id, String name, List<String> languages, int experienceYears, String contact, String specialization) {
        this.id = id;
        this.name = name;
        this.languages = languages;
        this.experienceYears = experienceYears;
        this.contact = contact;
        this.specialization = specialization;
        this.available = true;
        this.rating = 5.0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return name + " (" + experienceYears + " years exp.)";
    }
}
