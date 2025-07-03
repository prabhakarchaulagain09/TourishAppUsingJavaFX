package com.nepal.tourism.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Tourist tourist;
    private Guide guide;
    private Attraction attraction;
    private LocalDate bookingDate;
    private LocalDate trekDate;
    private String status; // Confirmed, Pending, Cancelled, Completed
    private double totalPrice;
    private boolean festivalDiscount;
    private String weatherInfo;
    private String emergencyNotes;

    public Booking() {
        this.bookingDate = LocalDate.now();
        this.status = "Pending";
    }

    public Booking(String id, Tourist tourist, Guide guide, Attraction attraction, LocalDate trekDate) {
        this.id = id;
        this.tourist = tourist;
        this.guide = guide;
        this.attraction = attraction;
        this.bookingDate = LocalDate.now();
        this.trekDate = trekDate;
        this.status = "Pending";
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.totalPrice = attraction.getPrice();
        if (isFestivalSeason()) {
            this.totalPrice *= 0.85; // 15% festival discount
            this.festivalDiscount = true;
        }
    }

    private boolean isFestivalSeason() {
        if (trekDate == null) {
            return false;
        }
        int month = trekDate.getMonthValue();
        // Dashain (September-October) and Tihar (October-November)
        return month == 9 || month == 10 || month == 11;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Tourist getTourist() { return tourist; }
    public void setTourist(Tourist tourist) { this.tourist = tourist; }

    public Guide getGuide() { return guide; }
    public void setGuide(Guide guide) { this.guide = guide; }

    public Attraction getAttraction() { return attraction; }
    public void setAttraction(Attraction attraction) { 
        this.attraction = attraction; 
        if (attraction != null) {
            calculateTotalPrice();
        }
    }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }

    public LocalDate getTrekDate() { return trekDate; }
    public void setTrekDate(LocalDate trekDate) { 
        this.trekDate = trekDate; 
        calculateTotalPrice();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public boolean isFestivalDiscount() { return festivalDiscount; }
    public void setFestivalDiscount(boolean festivalDiscount) { this.festivalDiscount = festivalDiscount; }

    public String getWeatherInfo() { return weatherInfo; }
    public void setWeatherInfo(String weatherInfo) { this.weatherInfo = weatherInfo; }

    public String getEmergencyNotes() { return emergencyNotes; }
    public void setEmergencyNotes(String emergencyNotes) { this.emergencyNotes = emergencyNotes; }

    @Override
    public String toString() {
        return tourist.getName() + " - " + attraction.getName() + " (" + status + ")";
    }
}
