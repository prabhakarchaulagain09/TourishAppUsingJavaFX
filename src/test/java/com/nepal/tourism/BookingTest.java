package com.nepal.tourism;

import com.nepal.tourism.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;

public class BookingTest {
    
    private Tourist tourist;
    private Guide guide;
    private Attraction attraction;
    private Booking booking;
    
    @BeforeEach
    void setUp() {
        tourist = new Tourist("T001", "John Doe", "USA", "+1-555-0123", "+1-555-0124", "john@email.com");
        guide = new Guide("G001", "Pemba Sherpa", Arrays.asList("English", "Nepali"), 10, "+977-98-1234-5678", "Trekking");
        attraction = new Attraction("A001", "Everest Base Camp", "Trek", "Khumbu", "Extreme", 5364, "Base camp trek", 2500.0);
        booking = new Booking("B001", tourist, guide, attraction, LocalDate.now().plusDays(30));
    }
    
    @Test
    void testBookingCreation() {
        assertNotNull(booking);
        assertEquals("B001", booking.getId());
        assertEquals(tourist, booking.getTourist());
        assertEquals(guide, booking.getGuide());
        assertEquals(attraction, booking.getAttraction());
        assertEquals("Pending", booking.getStatus());
    }
    
    @Test
    void testFestivalDiscountApplication() {
        // Test during festival season (October)
        LocalDate festivalDate = LocalDate.of(2024, 10, 15);
        Booking festivalBooking = new Booking("B002", tourist, guide, attraction, festivalDate);
        
        // Price should be 15% less during festival season
        double expectedPrice = attraction.getPrice() * 0.85;
        assertEquals(expectedPrice, festivalBooking.getTotalPrice(), 0.01);
        assertTrue(festivalBooking.isFestivalDiscount());
    }
    
    @Test
    void testRegularPricing() {
        // Test during non-festival season (January)
        LocalDate regularDate = LocalDate.of(2024, 1, 15);
        Booking regularBooking = new Booking("B003", tourist, guide, attraction, regularDate);
        
        assertEquals(attraction.getPrice(), regularBooking.getTotalPrice(), 0.01);
        assertFalse(regularBooking.isFestivalDiscount());
    }
    
    @Test
    void testHighAltitudeAttraction() {
        assertTrue(attraction.getAltitude() > 3000);
        assertTrue(attraction.isMonsoonRestricted());
    }
    
    @Test
    void testBookingStatusUpdate() {
        booking.setStatus("Confirmed");
        assertEquals("Confirmed", booking.getStatus());
        
        booking.setStatus("Completed");
        assertEquals("Completed", booking.getStatus());
    }
    
    @Test
    void testEmergencyNotes() {
        String emergencyNote = "Tourist reported altitude sickness symptoms";
        booking.setEmergencyNotes(emergencyNote);
        assertEquals(emergencyNote, booking.getEmergencyNotes());
    }
    
    @Test
    void testWeatherInfoStorage() {
        String weatherInfo = "Clear skies, -5°C, Light winds";
        booking.setWeatherInfo(weatherInfo);
        assertEquals(weatherInfo, booking.getWeatherInfo());
    }
}
