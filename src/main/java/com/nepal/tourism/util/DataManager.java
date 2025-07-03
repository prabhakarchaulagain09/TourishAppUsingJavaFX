package com.nepal.tourism.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nepal.tourism.model.*;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DATA_DIR = "data/";
    private static final String TOURISTS_FILE = DATA_DIR + "tourists.json";
    private static final String GUIDES_FILE = DATA_DIR + "guides.json";
    private static final String ATTRACTIONS_FILE = DATA_DIR + "attractions.json";
    private static final String BOOKINGS_FILE = DATA_DIR + "bookings.json";
    private static final String REPORTS_DIR = "reports/";
    
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();
    
    static {
        createDirectories();
    }
    
    private static void createDirectories() {
        new File(DATA_DIR).mkdirs();
        new File(REPORTS_DIR).mkdirs();
    }
    
    // Tourist Data Management
    public static void saveTourists(ObservableList<Tourist> tourists) {
        try (FileWriter writer = new FileWriter(TOURISTS_FILE)) {
            gson.toJson(tourists, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save tourists: " + e.getMessage(), e);
        }
    }
    
    public static List<Tourist> loadTourists() {
        try (FileReader reader = new FileReader(TOURISTS_FILE)) {
            Type listType = new TypeToken<List<Tourist>>(){}.getType();
            List<Tourist> tourists = gson.fromJson(reader, listType);
            return tourists != null ? tourists : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tourists: " + e.getMessage(), e);
        }
    }
    
    // Guide Data Management
    public static void saveGuides(ObservableList<Guide> guides) {
        try (FileWriter writer = new FileWriter(GUIDES_FILE)) {
            gson.toJson(guides, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save guides: " + e.getMessage(), e);
        }
    }
    
    public static List<Guide> loadGuides() {
        try (FileReader reader = new FileReader(GUIDES_FILE)) {
            Type listType = new TypeToken<List<Guide>>(){}.getType();
            List<Guide> guides = gson.fromJson(reader, listType);
            return guides != null ? guides : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load guides: " + e.getMessage(), e);
        }
    }
    
    // Attraction Data Management
    public static void saveAttractions(ObservableList<Attraction> attractions) {
        try (FileWriter writer = new FileWriter(ATTRACTIONS_FILE)) {
            gson.toJson(attractions, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save attractions: " + e.getMessage(), e);
        }
    }
    
    public static List<Attraction> loadAttractions() {
        try (FileReader reader = new FileReader(ATTRACTIONS_FILE)) {
            Type listType = new TypeToken<List<Attraction>>(){}.getType();
            List<Attraction> attractions = gson.fromJson(reader, listType);
            return attractions != null ? attractions : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load attractions: " + e.getMessage(), e);
        }
    }
    
    // Booking Data Management
    public static void saveBookings(ObservableList<Booking> bookings) {
        try (FileWriter writer = new FileWriter(BOOKINGS_FILE)) {
            gson.toJson(bookings, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save bookings: " + e.getMessage(), e);
        }
    }
    
    public static List<Booking> loadBookings() {
        try (FileReader reader = new FileReader(BOOKINGS_FILE)) {
            Type listType = new TypeToken<List<Booking>>(){}.getType();
            List<Booking> bookings = gson.fromJson(reader, listType);
            return bookings != null ? bookings : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load bookings: " + e.getMessage(), e);
        }
    }
    
    // Report Export
    public static void exportReport(ObservableList<Tourist> tourists, ObservableList<Guide> guides, 
                                  ObservableList<Attraction> attractions, ObservableList<Booking> bookings) {
        try {
            String filename = REPORTS_DIR + "tourism_report_" + LocalDate.now() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println("NEPAL TOURISM MANAGEMENT SYSTEM REPORT");
                writer.println("Generated on: " + LocalDate.now());
                writer.println("=" .repeat(50));
                
                writer.println("\nTOURIST STATISTICS:");
                writer.println("Total Tourists: " + tourists.size());
                tourists.stream()
                    .collect(java.util.stream.Collectors.groupingBy(Tourist::getNationality, 
                            java.util.stream.Collectors.counting()))
                    .forEach((nationality, count) -> 
                        writer.println("  " + nationality + ": " + count));
                
                writer.println("\nGUIDE STATISTICS:");
                writer.println("Total Guides: " + guides.size());
                writer.println("Average Experience: " + 
                    guides.stream().mapToInt(Guide::getExperienceYears).average().orElse(0) + " years");
                
                writer.println("\nATTRACTION STATISTICS:");
                writer.println("Total Attractions: " + attractions.size());
                attractions.stream()
                    .collect(java.util.stream.Collectors.groupingBy(Attraction::getType, 
                            java.util.stream.Collectors.counting()))
                    .forEach((type, count) -> 
                        writer.println("  " + type + ": " + count));
                
                writer.println("\nBOOKING STATISTICS:");
                writer.println("Total Bookings: " + bookings.size());
                double totalRevenue = bookings.stream().mapToDouble(Booking::getTotalPrice).sum();
                writer.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
                
                bookings.stream()
                    .collect(java.util.stream.Collectors.groupingBy(Booking::getStatus, 
                            java.util.stream.Collectors.counting()))
                    .forEach((status, count) -> 
                        writer.println("  " + status + ": " + count));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to export report: " + e.getMessage(), e);
        }
    }
}
