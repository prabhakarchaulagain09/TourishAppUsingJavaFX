package com.nepal.tourism.model;

import java.io.Serializable;

public class Attraction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String type; // Trek, Heritage, Adventure, Cultural
    private String location;
    private String difficulty; // Easy, Moderate, Hard, Extreme
    private int altitude;
    private String description;
    private double price;
    private boolean monsoonRestricted;

    public Attraction() {}

    public Attraction(String id, String name, String type, String location, String difficulty, int altitude, String description, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.difficulty = difficulty;
        this.altitude = altitude;
        this.description = description;
        this.price = price;
        this.monsoonRestricted = altitude > 3000 || type.equals("Trek");
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { 
        this.type = type; 
        updateMonsoonRestriction();
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getAltitude() { return altitude; }
    public void setAltitude(int altitude) { 
        this.altitude = altitude; 
        updateMonsoonRestriction();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isMonsoonRestricted() { return monsoonRestricted; }
    public void setMonsoonRestricted(boolean monsoonRestricted) { this.monsoonRestricted = monsoonRestricted; }

    private void updateMonsoonRestriction() {
        this.monsoonRestricted = altitude > 3000 || "Trek".equals(type);
    }

    @Override
    public String toString() {
        return name + " (" + location + ")";
    }
}
