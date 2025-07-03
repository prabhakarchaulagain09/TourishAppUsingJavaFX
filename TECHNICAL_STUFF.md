# Nepal Tourism Management System - Complete Technical Report

## Table of Contents
1. [System Overview](#system-overview)
2. [Architecture Analysis](#architecture-analysis)
3. [Data Models Deep Dive](#data-models-deep-dive)
4. [Controller Layer Analysis](#controller-layer-analysis)
5. [Service Layer Implementation](#service-layer-implementation)
6. [Data Persistence System](#data-persistence-system)
7. [User Interface Architecture](#user-interface-architecture)
8. [Business Logic Implementation](#business-logic-implementation)
9. [Integration Points](#integration-points)
10. [Error Handling & Validation](#error-handling--validation)
11. [Performance Considerations](#performance-considerations)
12. [Security Analysis](#security-analysis)

---

## System Overview

The Nepal Tourism Management System is a comprehensive JavaFX desktop application designed to manage tourism operations in Nepal. It handles tourist registration, guide management, attraction cataloging, booking processes, weather integration, and emergency management.

### Core Functionality Matrix
```
┌─────���───────────┬──────────────┬──────────────┬──────────────┬──────────────┐
│ Entity          │ Create       │ Read         │ Update       │ Delete       │
├─────────────────┼──────────────┼──────────────┼──────────────┼──────────────┤
│ Tourist         │ ✓ Form Input │ ✓ Table View │ ✓ Edit Form  │ ✓ Selection  │
│ Guide           │ ✓ Form Input │ ✓ Table View │ ✓ Edit Form  │ ✓ Selection  │
│ Attraction      │ ✓ Form Input │ ✓ Table View │ ✓ Edit Form  │ ✓ Selection  │
│ Booking         │ ✓ Complex    │ ✓ Table View │ ✓ Edit Form  │ ✓ Selection  │
│ Emergency Log   │ ✓ Report     │ ✓ List View  │ ✗ Read-only  │ ✗ Permanent  │
└─────────────────┴──────────────┴──────────────┴──────────────┴──────────────┘
```

---

## Architecture Analysis

### 1. Application Entry Point (`TourismApp.java`)

```java
public class TourismApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Load FXML resource
        var resource = getClass().getResource("/fxml/MainView.fxml");
        
        // 2. Create FXML loader
        FXMLLoader loader = new FXMLLoader(resource);
        
        // 3. Load scene from FXML
        Scene scene = new Scene(loader.load(), 1200, 800);
        
        // 4. Configure primary stage
        primaryStage.setTitle("Nepal Tourism Management System");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
```

**How it works:**
1. **JavaFX Application Lifecycle**: Extends `Application` class, entry point for JavaFX
2. **FXML Loading**: Uses reflection to load UI layout from XML file
3. **Scene Graph Creation**: Creates scene with specified dimensions
4. **Stage Configuration**: Sets up main window properties

### 2. MVC Architecture Implementation

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
├─────────────────────────────────────────────────────────────┤
│ MainView.fxml (English) │ MainView_np.fxml (Nepali)        │
│ - Tab-based interface   │ - Localized UI elements          │
│ - Form controls         │ - Cultural adaptations           │
│ - Data tables          │ - Right-to-left considerations   │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                         │
├─────────────────────────────────────────────────────────────┤
│ MainController.java                                         │
│ - Event handling        │ - Data binding                    │
│ - UI state management   │ - Validation logic                │
│ - Business orchestration│ - Error handling                  │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                           │
├─────────────────────────────────────────────────────────────┤
│ WeatherService.java     │ DataManager.java                  │
│ - External API calls    │ - Data persistence                │
│ - Weather data parsing  │ - File I/O operations             │
│ - Safety warnings       │ - JSON serialization              │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                      MODEL LAYER                            │
├─────────────────────────────────────────────────────────────┤
│ Tourist.java │ Guide.java │ Attraction.java │ Booking.java   │
│ - Data structure        │ - Business rules                  │
│ - Validation logic      │ - Relationships                   │
│ - Serialization support │ - Calculated fields               │
└─────────────────────────────────────────────────────────────┘
```

---

## Data Models Deep Dive

### 1. Tourist Model Analysis

```java
public class Tourist implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Core identification
    private String id;              // Unique identifier (T001, T002, etc.)
    private String name;            // Full name
    private String nationality;     // Country of origin
    
    // Contact information
    private String contact;         // Primary phone number
    private String emergencyContact; // Emergency contact number
    private String email;           // Email address
    
    // System metadata
    private LocalDate registrationDate; // Auto-set to current date
}
```

**Data Flow Example:**
```json
{
  "id": "T001",
  "name": "John Smith",
  "nationality": "USA",
  "contact": "+1-555-0123",
  "emergencyContact": "+1-555-0124",
  "email": "john@email.com",
  "registrationDate": "2025-06-30"
}
```

**Business Rules:**
- ID must be unique across all tourists
- Registration date is automatically set to current date
- Emergency contact is mandatory for safety protocols
- Nationality is used for analytics and cultural considerations

### 2. Guide Model Analysis

```java
public class Guide implements Serializable {
    private String id;                    // Unique identifier
    private String name;                  // Guide's full name
    private List<String> languages;       // Spoken languages
    private int experienceYears;          // Years of experience
    private String contact;               // Contact information
    private String specialization;        // Area of expertise
    private double rating;                // Performance rating (default 5.0)
    private boolean available;            // Availability status
}
```

**Data Storage Example:**
```json
{
  "id": "G001",
  "name": "Pemba Sherpa",
  "languages": ["English", "Nepali", "Tibetan"],
  "experienceYears": 15,
  "contact": "+977-98-1234-5678",
  "specialization": "High Altitude Trekking",
  "rating": 5.0,
  "available": true
}
```

**Specialization Categories:**
- High Altitude Trekking
- Cultural Tours
- Adventure Sports
- Wildlife Safaris
- Pilgrimage Tours
- Photography Tours

### 3. Attraction Model Analysis

```java
public class Attraction implements Serializable {
    private String id;                    // Unique identifier
    private String name;                  // Attraction name
    private String type;                  // Category type
    private String location;              // Geographic location
    private String difficulty;            // Difficulty level
    private int altitude;                 // Elevation in meters
    private String description;           // Detailed description
    private double price;                 // Base price in USD
    private boolean monsoonRestricted;    // Safety restriction flag
    
    // Business logic method
    private void updateMonsoonRestriction() {
        this.monsoonRestricted = altitude > 3000 || "Trek".equals(type);
    }
}
```

**Data Example:**
```json
{
  "id": "A001",
  "name": "Everest Base Camp Trek",
  "type": "Trek",
  "location": "Khumbu",
  "difficulty": "Extreme",
  "altitude": 5364,
  "description": "World's highest base camp trek",
  "price": 2500.0,
  "monsoonRestricted": true
}
```

**Difficulty Classification:**
```
Easy (0-2000m):     Cultural sites, city tours
Moderate (2000-3500m): Day hikes, moderate treks  
Hard (3500-5000m):     Multi-day treks, technical routes
Extreme (5000m+):      High-altitude expeditions
```

### 4. Booking Model - The Core Business Entity

```java
public class Booking implements Serializable {
    private String id;                    // Unique booking identifier
    private Tourist tourist;              // Associated tourist (composition)
    private Guide guide;                  // Assigned guide (composition)
    private Attraction attraction;        // Booked attraction (composition)
    private LocalDate bookingDate;        // When booking was made
    private LocalDate trekDate;           // Scheduled activity date
    private String status;                // Current booking status
    private double totalPrice;            // Calculated final price
    private boolean festivalDiscount;     // Discount flag
    private String weatherInfo;           // Weather data snapshot
    private String emergencyNotes;        // Safety notes
    
    // Complex business logic
    private void calculateTotalPrice() {
        this.totalPrice = attraction.getPrice();
        if (isFestivalSeason()) {
            this.totalPrice *= 0.85; // 15% festival discount
            this.festivalDiscount = true;
        }
    }
    
    private boolean isFestivalSeason() {
        if (trekDate == null) return false;
        int month = trekDate.getMonthValue();
        return month == 9 || month == 10 || month == 11; // Dashain & Tihar
    }
}
```

**Complete Booking Data Example:**
```json
{
  "id": "B001",
  "tourist": {
    "id": "T001",
    "name": "John Smith",
    "nationality": "USA",
    "contact": "+1-555-0123",
    "emergencyContact": "+1-555-0124",
    "email": "john@email.com",
    "registrationDate": "2025-06-30"
  },
  "guide": {
    "id": "G001",
    "name": "Pemba Sherpa",
    "languages": ["English", "Nepali", "Tibetan"],
    "experienceYears": 15,
    "contact": "+977-98-1234-5678",
    "specialization": "High Altitude Trekking",
    "rating": 5.0,
    "available": true
  },
  "attraction": {
    "id": "A001",
    "name": "Everest Base Camp Trek",
    "type": "Trek",
    "location": "Khumbu",
    "difficulty": "Extreme",
    "altitude": 5364,
    "description": "World's highest base camp trek",
    "price": 2500.0,
    "monsoonRestricted": true
  },
  "bookingDate": "2025-06-30",
  "trekDate": "2025-10-15",
  "status": "Confirmed",
  "totalPrice": 2125.0,
  "festivalDiscount": true,
  "weatherInfo": "Temperature: 22°C\nCondition: overcast clouds...",
  "emergencyNotes": null
}
```

---

## Controller Layer Analysis

### MainController - The System Orchestrator

The `MainController` is the heart of the application, managing all user interactions and business logic coordination.

#### 1. Initialization Process

```java
@Override
public void initialize(URL location, ResourceBundle resources) {
    // Step 1: Set up UI components
    initializeTableColumns();
    
    // Step 2: Configure dropdown menus
    initializeComboBoxes();
    
    // Step 3: Load sample/existing data
    loadSampleData();
    loadDataFromFiles();
    
    // Step 4: Update analytics dashboard
    updateAnalytics();
    
    // Step 5: Set up event handlers
    setupEventHandlers();
}
```

**Detailed Breakdown:**

**Table Column Initialization:**
```java
private void initializeTableColumns() {
    // Tourist table - Property binding to model fields
    touristIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    touristNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    touristNationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
    touristContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
    
    // Booking table - Complex property binding with nested objects
    bookingTouristColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getTourist().getName()));
    bookingAttractionColumn.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getAttraction().getName()));
    
    // Bind ObservableList to TableView
    touristTable.setItems(tourists);
    guideTable.setItems(guides);
    attractionTable.setItems(attractions);
    bookingTable.setItems(bookings);
}
```

#### 2. CRUD Operations Implementation

**Tourist Management Example:**

```java
@FXML
private void addTourist() {
    try {
        // 1. Create new Tourist object from form data
        Tourist tourist = new Tourist(
            touristIdField.getText(),           // Get ID from text field
            touristNameField.getText(),         // Get name from text field
            touristNationalityField.getText(),  // Get nationality
            touristContactField.getText(),      // Get contact
            touristEmergencyField.getText(),    // Get emergency contact
            touristEmailField.getText()         // Get email
        );
        
        // 2. Add to ObservableList (automatically updates UI)
        tourists.add(tourist);
        
        // 3. Clear form fields for next entry
        clearTouristFields();
        
        // 4. Persist to JSON file
        DataManager.saveTourists(tourists);
        
        // 5. Update analytics charts
        updateAnalytics();
        
        // 6. Show success message
        showAlert("Success", "Tourist added successfully!");
        
    } catch (Exception e) {
        // 7. Handle any errors
        showAlert("Error", "Failed to add tourist: " + e.getMessage());
    }
}
```

**Update Operation Flow:**
```java
@FXML
private void updateTourist() {
    // 1. Get selected item from table
    Tourist selected = touristTable.getSelectionModel().getSelectedItem();
    
    if (selected != null) {
        // 2. Update object properties with form data
        selected.setId(touristIdField.getText());
        selected.setName(touristNameField.getText());
        selected.setNationality(touristNationalityField.getText());
        selected.setContact(touristContactField.getText());
        selected.setEmergencyContact(touristEmergencyField.getText());
        selected.setEmail(touristEmailField.getText());
        
        // 3. Refresh table view to show changes
        touristTable.refresh();
        
        // 4. Persist changes
        DataManager.saveTourists(tourists);
        
        // 5. Update analytics
        updateAnalytics();
        
        showAlert("Success", "Tourist updated successfully!");
    }
}
```

#### 3. Complex Booking Management

**Booking Creation with Business Logic:**

```java
@FXML
private void addBooking() {
    try {
        // 1. Safety check for monsoon restrictions
        if (isMonsoonRestricted()) {
            showAlert("Warning", 
                "This trek is restricted during monsoon season (June-August) for safety reasons!");
            return;
        }
        
        // 2. Create booking with selected entities
        Booking booking = new Booking(
            bookingIdField.getText(),
            bookingTouristCombo.getValue(),    // Selected Tourist object
            bookingGuideCombo.getValue(),      // Selected Guide object
            bookingAttractionCombo.getValue(), // Selected Attraction object
            bookingDatePicker.getValue()       // Selected date
        );
        
        // 3. Set additional properties
        booking.setStatus(bookingStatusCombo.getValue());
        booking.setWeatherInfo(weatherInfoArea.getText());
        
        // 4. Add to collection and persist
        bookings.add(booking);
        clearBookingFields();
        DataManager.saveBookings(bookings);
        updateAnalytics();
        
        // 5. Show success with discount information
        showAlert("Success", "Booking added successfully!" + 
            (booking.isFestivalDiscount() ? " Festival discount applied!" : ""));
            
    } catch (Exception e) {
        showAlert("Error", "Failed to add booking: " + e.getMessage());
    }
}
```

**Monsoon Restriction Logic:**
```java
private boolean isMonsoonRestricted() {
    Attraction attraction = bookingAttractionCombo.getValue();
    LocalDate trekDate = bookingDatePicker.getValue();
    
    if (attraction != null && trekDate != null && attraction.isMonsoonRestricted()) {
        int month = trekDate.getMonthValue();
        return month >= 6 && month <= 8; // June to August monsoon season
    }
    return false;
}
```

#### 4. Dynamic Price Calculation

```java
private void updateBookingPrice() {
    Attraction attraction = bookingAttractionCombo.getValue();
    LocalDate date = bookingDatePicker.getValue();
    
    if (attraction != null) {
        double price = attraction.getPrice();
        boolean festivalDiscount = false;
        
        // Check for festival season discount
        if (date != null) {
            int month = date.getMonthValue();
            if (month == 9 || month == 10 || month == 11) { // Dashain & Tihar
                price *= 0.85; // 15% discount
                festivalDiscount = true;
            }
        }
        
        // Update UI elements
        bookingPriceLabel.setText(String.format("$%.2f", price));
        festivalDiscountLabel.setText(festivalDiscount ? "Festival Discount Applied!" : "");
        festivalDiscountLabel.setVisible(festivalDiscount);
    }
}
```

#### 5. Event Handler Setup

```java
private void setupEventHandlers() {
    // Weather button handler
    getWeatherButton.setOnAction(e -> updateWeatherInfo());
    
    // Booking attraction selection handler - triggers price update
    bookingAttractionCombo.setOnAction(e -> {
        updateBookingPrice();
        updateWeatherInfo();
    });
    
    // Date picker handler - triggers price recalculation
    bookingDatePicker.setOnAction(e -> updateBookingPrice());
    
    // Table selection handlers - populate form fields when row selected
    touristTable.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateTouristFields(newSelection);
        });
    
    // Similar handlers for other tables...
}
```

---

## Service Layer Implementation

### 1. WeatherService - External API Integration

The WeatherService integrates with OpenWeatherMap API to provide real-time weather data and safety warnings.

```java
public class WeatherService {
    private static final String API_KEY = "e589adaace8affb09ab7f93e4d7cf4d9";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    public static WeatherInfo getWeatherForLocation(String location) {
        try {
            // 1. Build API URL with parameters
            String urlString = BASE_URL + "?q=" + location + ",NP&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            
            // 2. Create HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // 3. Read response
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // 4. Parse JSON response
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject main = jsonObject.getAsJsonObject("main");
            JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
            JsonObject wind = jsonObject.getAsJsonObject("wind");
            
            // 5. Extract weather data
            String temperature = String.valueOf(Math.round(main.get("temp").getAsDouble()));
            String description = weather.get("description").getAsString();
            String humidity = String.valueOf(main.get("humidity").getAsInt());
            String windSpeed = String.valueOf(Math.round(wind.get("speed").getAsDouble() * 3.6));
            String visibility = jsonObject.has("visibility") ? 
                String.valueOf(jsonObject.get("visibility").getAsInt() / 1000) : "N/A";
            
            // 6. Generate safety warnings
            String warning = generateWeatherWarning(temperature, description, windSpeed);
            
            return new WeatherInfo(temperature, description, humidity, windSpeed, visibility, warning);
            
        } catch (Exception e) {
            // 7. Return error state with user-friendly message
            return new WeatherInfo("N/A", "Unable to fetch weather data", "N/A", "N/A", "N/A", 
                "Weather service unavailable. Please check your internet connection.");
        }
    }
}
```

**Weather Warning Generation:**
```java
private static String generateWeatherWarning(String temperature, String description, String windSpeed) {
    StringBuilder warning = new StringBuilder();
    
    try {
        int temp = Integer.parseInt(temperature);
        int wind = Integer.parseInt(windSpeed);
        
        // Temperature-based warnings
        if (temp < 0) {
            warning.append("Extreme cold conditions. Risk of frostbite. ");
        } else if (temp < 5) {
            warning.append("Very cold conditions. Proper winter gear required. ");
        }
        
        // Wind-based warnings
        if (wind > 50) {
            warning.append("High wind speeds. Dangerous for high-altitude activities. ");
        } else if (wind > 30) {
            warning.append("Strong winds. Exercise caution during outdoor activities. ");
        }
        
        // Condition-based warnings
        if (description.toLowerCase().contains("rain") || description.toLowerCase().contains("storm")) {
            warning.append("Wet conditions. Slippery trails and reduced visibility. ");
        }
        
        if (description.toLowerCase().contains("snow")) {
            warning.append("Snow conditions. Avalanche risk in mountainous areas. ");
        }
        
    } catch (NumberFormatException e) {
        // Handle parsing errors silently
    }
    
    return warning.toString().trim();
}
```

**Location Mapping for Attractions:**
```java
public static WeatherInfo getWeatherForAttraction(String attractionName, String location) {
    // Map attraction names to specific weather stations for accuracy
    String weatherLocation = location;
    
    if (attractionName.toLowerCase().contains("everest")) {
        weatherLocation = "Namche Bazaar";  // Closest weather station to EBC
    } else if (attractionName.toLowerCase().contains("annapurna")) {
        weatherLocation = "Pokhara";        // Gateway to Annapurna region
    } else if (attractionName.toLowerCase().contains("langtang")) {
        weatherLocation = "Dhunche";        // Langtang region access point
    }
    
    return getWeatherForLocation(weatherLocation);
}
```

### 2. DataManager - Persistence Layer

The DataManager handles all data persistence operations using JSON files as the storage medium.

```java
public class DataManager {
    // File system structure
    private static final String DATA_DIR = "data/";
    private static final String TOURISTS_FILE = DATA_DIR + "tourists.json";
    private static final String GUIDES_FILE = DATA_DIR + "guides.json";
    private static final String ATTRACTIONS_FILE = DATA_DIR + "attractions.json";
    private static final String BOOKINGS_FILE = DATA_DIR + "bookings.json";
    private static final String REPORTS_DIR = "reports/";
    
    // JSON serialization configuration
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();
    
    static {
        createDirectories(); // Ensure directories exist on class loading
    }
}
```

**Save Operation Implementation:**
```java
public static void saveTourists(ObservableList<Tourist> tourists) {
    try (FileWriter writer = new FileWriter(TOURISTS_FILE)) {
        // Convert ObservableList to JSON and write to file
        gson.toJson(tourists, writer);
    } catch (IOException e) {
        throw new RuntimeException("Failed to save tourists: " + e.getMessage(), e);
    }
}
```

**Load Operation Implementation:**
```java
public static List<Tourist> loadTourists() {
    try (FileReader reader = new FileReader(TOURISTS_FILE)) {
        // Define generic type for JSON deserialization
        Type listType = new TypeToken<List<Tourist>>(){}.getType();
        List<Tourist> tourists = gson.fromJson(reader, listType);
        return tourists != null ? tourists : new ArrayList<>();
    } catch (FileNotFoundException e) {
        // File doesn't exist yet - return empty list
        return new ArrayList<>();
    } catch (IOException e) {
        throw new RuntimeException("Failed to load tourists: " + e.getMessage(), e);
    }
}
```

**Report Generation:**
```java
public static void exportReport(ObservableList<Tourist> tourists, ObservableList<Guide> guides, 
                              ObservableList<Attraction> attractions, ObservableList<Booking> bookings) {
    try {
        String filename = REPORTS_DIR + "tourism_report_" + LocalDate.now() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("NEPAL TOURISM MANAGEMENT SYSTEM REPORT");
            writer.println("Generated on: " + LocalDate.now());
            writer.println("=".repeat(50));
            
            // Tourist statistics with nationality breakdown
            writer.println("\nTOURIST STATISTICS:");
            writer.println("Total Tourists: " + tourists.size());
            tourists.stream()
                .collect(Collectors.groupingBy(Tourist::getNationality, Collectors.counting()))
                .forEach((nationality, count) -> 
                    writer.println("  " + nationality + ": " + count));
            
            // Guide statistics
            writer.println("\nGUIDE STATISTICS:");
            writer.println("Total Guides: " + guides.size());
            writer.println("Average Experience: " + 
                guides.stream().mapToInt(Guide::getExperienceYears).average().orElse(0) + " years");
            
            // Attraction statistics by type
            writer.println("\nATTRACTION STATISTICS:");
            writer.println("Total Attractions: " + attractions.size());
            attractions.stream()
                .collect(Collectors.groupingBy(Attraction::getType, Collectors.counting()))
                .forEach((type, count) -> 
                    writer.println("  " + type + ": " + count));
            
            // Booking and revenue statistics
            writer.println("\nBOOKING STATISTICS:");
            writer.println("Total Bookings: " + bookings.size());
            double totalRevenue = bookings.stream().mapToDouble(Booking::getTotalPrice).sum();
            writer.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
            
            // Booking status breakdown
            bookings.stream()
                .collect(Collectors.groupingBy(Booking::getStatus, Collectors.counting()))
                .forEach((status, count) -> 
                    writer.println("  " + status + ": " + count));
        }
    } catch (IOException e) {
        throw new RuntimeException("Failed to export report: " + e.getMessage(), e);
    }
}
```

---

## Data Storage Deep Dive

### File System Structure

```
TourishAppUsingJavaFX/
├── data/
│   ├── tourists.json      # Tourist records
│   ├── guides.json        # Guide profiles
│   ├── attractions.json   # Attraction catalog
│   └── bookings.json      # Booking transactions
├── reports/
│   └── tourism_report_YYYY-MM-DD.txt
└── src/
    └── main/
        └── resources/
            └── fxml/
                ├── MainView.fxml      # English UI
                └── MainView_np.fxml   # Nepali UI
```

### JSON Data Examples

**tourists.json Structure:**
```json
[
  {
    "id": "T001",
    "name": "John Smith",
    "nationality": "USA",
    "contact": "+1-555-0123",
    "emergencyContact": "+1-555-0124",
    "email": "john@email.com",
    "registrationDate": "2025-06-30"
  },
  {
    "id": "T002",
    "name": "Emma Wilson",
    "nationality": "UK",
    "contact": "+44-20-7946-0958",
    "emergencyContact": "+44-20-7946-0959",
    "email": "emma@email.com",
    "registrationDate": "2025-06-30"
  }
]
```

**bookings.json Structure (Complex Nested Objects):**
```json
[
  {
    "id": "B001",
    "tourist": {
      "id": "T001",
      "name": "John Smith",
      "nationality": "USA",
      "contact": "+1-555-0123",
      "emergencyContact": "+1-555-0124",
      "email": "john@email.com",
      "registrationDate": "2025-06-30"
    },
    "guide": {
      "id": "G001",
      "name": "Pemba Sherpa",
      "languages": ["English", "Nepali", "Tibetan"],
      "experienceYears": 15,
      "contact": "+977-98-1234-5678",
      "specialization": "High Altitude Trekking",
      "rating": 5.0,
      "available": true
    },
    "attraction": {
      "id": "A001",
      "name": "Everest Base Camp Trek",
      "type": "Trek",
      "location": "Khumbu",
      "difficulty": "Extreme",
      "altitude": 5364,
      "description": "World's highest base camp trek",
      "price": 2500.0,
      "monsoonRestricted": true
    },
    "bookingDate": "2025-06-30",
    "trekDate": "2025-10-15",
    "status": "Confirmed",
    "totalPrice": 2125.0,
    "festivalDiscount": true,
    "weatherInfo": "Temperature: 22°C\nCondition: overcast clouds\nHumidity: 76%\nWind: 7 km/h\nVisibility: 10 km\n\n🏔️ HIGH ALTITUDE WARNING:\nRisk of altitude sickness above 3000m. Acclimatization recommended.\nSymptoms: Headache, nausea, fatigue, dizziness.\nSeek immediate medical attention if symptoms worsen."
  }
]
```

### LocalDate Serialization

Custom adapter for handling Java 8 LocalDate serialization:

```java
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(src));
    }
    
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}
```

---

## User Interface Architecture

### FXML Structure Analysis

The UI is built using FXML (JavaFX Markup Language) with separate files for different languages.

**MainView.fxml Structure:**
```xml
<BorderPane>
    <top>
        <!-- Header with title and language toggle -->
        <HBox alignment="CENTER_LEFT" spacing="10.0" styleClass="header">
            <Label styleClass="title" text="Nepal Tourism Management System" />
            <Region HBox.hgrow="ALWAYS" />
            <Button fx:id="languageToggleButton" onAction="#toggleLanguage" text="नेपाली" />
        </HBox>
    </top>
    <center>
        <!-- Main content area with tabs -->
        <TabPane fx:id="mainTabPane" tabClosingPolicy="UNAVAILABLE">
            <!-- Tourist Management Tab -->
            <Tab text="Tourists">
                <VBox spacing="10.0">
                    <!-- Form fields for tourist data entry -->
                    <GridPane hgap="10.0" vgap="10.0">
                        <Label text="Tourist ID:" GridPane.columnIndex="0" GridPane.rowIndex="0" />
                        <TextField fx:id="touristIdField" GridPane.columnIndex="1" GridPane.rowIndex="0" />
                        <!-- More form fields... -->
                    </GridPane>
                    
                    <!-- Action buttons -->
                    <HBox spacing="10.0">
                        <Button onAction="#addTourist" styleClass="primary-button" text="Add Tourist" />
                        <Button onAction="#updateTourist" styleClass="secondary-button" text="Update Tourist" />
                        <Button onAction="#deleteTourist" styleClass="danger-button" text="Delete Tourist" />
                    </HBox>
                    
                    <!-- Data table -->
                    <TableView fx:id="touristTable" VBox.vgrow="ALWAYS">
                        <columns>
                            <TableColumn fx:id="touristIdColumn" prefWidth="100.0" text="ID" />
                            <TableColumn fx:id="touristNameColumn" prefWidth="150.0" text="Name" />
                            <!-- More columns... -->
                        </columns>
                    </TableView>
                </VBox>
            </Tab>
            <!-- More tabs for Guides, Attractions, Bookings, Analytics, Emergency -->
        </TabPane>
    </center>
</BorderPane>
```

### Language Switching Implementation

```java
@FXML
public void toggleLanguage() {
    try {
        // Determine current language state
        boolean isCurrentlyNepali = languageToggleButton.getText().equals("English");
        
        // Select appropriate FXML file
        String fxmlFile = isCurrentlyNepali ? "/fxml/MainView.fxml" : "/fxml/MainView_np.fxml";
        
        // Load new FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = languageToggleButton.getScene();
        scene.setRoot(loader.load());
        
        // Transfer data to new controller instance
        MainController newController = loader.getController();
        newController.initializeWithData(tourists, guides, attractions, bookings);
        
    } catch (Exception e) {
        System.err.println("Failed to change language: " + e.getMessage());
        e.printStackTrace();
    }
}
```

---

## Business Logic Implementation

### 1. Festival Discount System

The system automatically applies discounts during Nepal's major festivals:

```java
private boolean isFestivalSeason() {
    if (trekDate == null) {
        return false;
    }
    int month = trekDate.getMonthValue();
    // Dashain (September-October) and Tihar (October-November)
    return month == 9 || month == 10 || month == 11;
}

private void calculateTotalPrice() {
    this.totalPrice = attraction.getPrice();
    if (isFestivalSeason()) {
        this.totalPrice *= 0.85; // 15% festival discount
        this.festivalDiscount = true;
    }
}
```

**Festival Calendar:**
- **Dashain** (September-October): Nepal's biggest festival
- **Tihar** (October-November): Festival of lights
- **15% Discount** applied automatically for bookings during these months

### 2. Safety Management System

**Monsoon Restrictions:**
```java
private void updateMonsoonRestriction() {
    // Automatically restrict high-altitude treks and all treks during monsoon
    this.monsoonRestricted = altitude > 3000 || "Trek".equals(type);
}

private boolean isMonsoonRestricted() {
    Attraction attraction = bookingAttractionCombo.getValue();
    LocalDate trekDate = bookingDatePicker.getValue();
    
    if (attraction != null && trekDate != null && attraction.isMonsoonRestricted()) {
        int month = trekDate.getMonthValue();
        return month >= 6 && month <= 8; // June to August
    }
    return false;
}
```

**Weather-Based Safety Warnings:**
```java
// Altitude sickness warning for high-altitude destinations
if (selectedAttraction.getAltitude() > 3000) {
    weatherInfoArea.appendText("\n\n🏔️ HIGH ALTITUDE WARNING:\n" +
        "Risk of altitude sickness above 3000m. Acclimatization recommended.\n" +
        "Symptoms: Headache, nausea, fatigue, dizziness.\n" +
        "Seek immediate medical attention if symptoms worsen.");
}
```

### 3. Emergency Management

```java
@FXML
private void reportEmergency() {
    Booking selectedBooking = emergencyBookingCombo.getValue();
    String notes = emergencyNotesArea.getText();
    
    if (selectedBooking != null && !notes.trim().isEmpty()) {
        // Create timestamped emergency log entry
        String emergencyLog = String.format("[%s] EMERGENCY - Booking: %s, Tourist: %s, Notes: %s",
            LocalDate.now().toString(),
            selectedBooking.getId(),
            selectedBooking.getTourist().getName(),
            notes);
        
        // Add to emergency log
        emergencyLogs.add(emergencyLog);
        emergencyLogList.getItems().add(emergencyLog);
        
        // Update booking with emergency notes
        selectedBooking.setEmergencyNotes(notes);
        emergencyNotesArea.clear();
        
        showAlert("Emergency Reported", 
            "Emergency has been logged and relevant authorities will be notified.");
    }
}
```

### 4. Analytics Engine

**Nationality Distribution Analysis:**
```java
private void updateNationalityChart() {
    // Group tourists by nationality and count
    Map<String, Long> nationalityCount = tourists.stream()
        .collect(Collectors.groupingBy(Tourist::getNationality, Collectors.counting()));
    
    // Convert to PieChart data
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    nationalityCount.forEach((nationality, count) -> 
        pieChartData.add(new PieChart.Data(nationality, count)));
    
    nationalityChart.setData(pieChartData);
}
```

**Popular Attractions Analysis:**
```java
private void updateAttractionChart() {
    // Count bookings per attraction
    Map<String, Long> attractionCount = bookings.stream()
        .collect(Collectors.groupingBy(
            booking -> booking.getAttraction().getName(), 
            Collectors.counting()));
    
    // Create bar chart series
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Bookings per Attraction");
    
    attractionCount.forEach((attraction, count) -> 
        series.getData().add(new XYChart.Data<>(attraction, count)));
    
    attractionChart.getData().clear();
    attractionChart.getData().add(series);
}
```

**Revenue Calculation:**
```java
private void updateStatistics() {
    totalTouristsLabel.setText("Total Tourists: " + tourists.size());
    totalBookingsLabel.setText("Total Bookings: " + bookings.size());
    
    // Calculate total revenue from all bookings
    double totalRevenue = bookings.stream()
        .mapToDouble(Booking::getTotalPrice)
        .sum();
    totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", totalRevenue));
}
```

---

## Integration Points

### 1. Data Flow Between Components

```
User Input (FXML) 
    ↓
Controller Event Handler
    ↓
Model Object Creation/Update
    ↓
ObservableList Update (triggers UI refresh)
    ↓
DataManager Persistence
    ↓
JSON File Storage
```

### 2. Weather Service Integration

```
User Selects Attraction
    ↓
Controller calls updateWeatherInfo()
    ↓
WeatherService.getWeatherForAttraction()
    ↓
HTTP API Call to OpenWeatherMap
    ↓
JSON Response Parsing
    ↓
WeatherInfo Object Creation
    ↓
Safety Warning Generation
    ↓
UI Update with Weather Data
```

### 3. Analytics Update Chain

```
CRUD Operation (Add/Update/Delete)
    ↓
updateAnalytics() called
    ↓
updateNationalityChart()
updateAttractionChart()
updateStatistics()
    ↓
Stream API Data Processing
    ↓
Chart Data Update
    ↓
UI Refresh
```

---

## Error Handling & Validation

### 1. Input Validation

```java
@FXML
private void addTourist() {
    try {
        // Validation could be added here
        if (touristIdField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Tourist ID is required");
            return;
        }
        
        // Create tourist object
        Tourist tourist = new Tourist(/* parameters */);
        
        // Add to collection
        tourists.add(tourist);
        
        // Success operations...
        
    } catch (Exception e) {
        // Generic error handling
        showAlert("Error", "Failed to add tourist: " + e.getMessage());
    }
}
```

### 2. File I/O Error Handling

```java
public static List<Tourist> loadTourists() {
    try (FileReader reader = new FileReader(TOURISTS_FILE)) {
        Type listType = new TypeToken<List<Tourist>>(){}.getType();
        List<Tourist> tourists = gson.fromJson(reader, listType);
        return tourists != null ? tourists : new ArrayList<>();
    } catch (FileNotFoundException e) {
        // File doesn't exist - return empty list (first run)
        return new ArrayList<>();
    } catch (IOException e) {
        // Serious I/O error - propagate as runtime exception
        throw new RuntimeException("Failed to load tourists: " + e.getMessage(), e);
    }
}
```

### 3. Network Error Handling

```java
public static WeatherInfo getWeatherForLocation(String location) {
    try {
        // API call implementation...
        return new WeatherInfo(/* parsed data */);
        
    } catch (Exception e) {
        // Return error state instead of throwing exception
        return new WeatherInfo("N/A", "Unable to fetch weather data", "N/A", "N/A", "N/A", 
            "Weather service unavailable. Please check your internet connection.");
    }
}
```

---

## Performance Considerations

### 1. ObservableList Efficiency

```java
// Efficient: Single operation triggers one UI update
tourists.addAll(loadedTourists);

// Inefficient: Multiple operations trigger multiple UI updates
for (Tourist tourist : loadedTourists) {
    tourists.add(tourist); // Each add triggers table refresh
}
```

### 2. Stream API Usage

```java
// Efficient: Single pass through data
Map<String, Long> nationalityCount = tourists.stream()
    .collect(Collectors.groupingBy(Tourist::getNationality, Collectors.counting()));

// Less efficient: Multiple iterations
Map<String, Long> nationalityCount = new HashMap<>();
for (String nationality : getAllNationalities()) {
    long count = tourists.stream()
        .filter(t -> t.getNationality().equals(nationality))
        .count(); // Separate iteration for each nationality
    nationalityCount.put(nationality, count);
}
```

### 3. Memory Management

```java
// Proper resource management with try-with-resources
try (FileWriter writer = new FileWriter(TOURISTS_FILE)) {
    gson.toJson(tourists, writer);
} // FileWriter automatically closed

// Avoid memory leaks in event handlers
touristTable.getSelectionModel().selectedItemProperty().addListener(
    (obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            populateTouristFields(newSelection);
        }
    });
```

---

## Security Analysis

### 1. Data Exposure

**Current State:**
- API key hardcoded in source code
- No encryption for stored data
- No user authentication

**Recommendations:**
```java
// Better: Load API key from environment variable
private static final String API_KEY = System.getenv("WEATHER_API_KEY");

// Better: Encrypt sensitive data before storage
public static void saveTourists(ObservableList<Tourist> tourists) {
    String encryptedData = encrypt(gson.toJson(tourists));
    // Save encrypted data
}
```

### 2. Input Sanitization

**Current State:**
- Limited input validation
- No SQL injection protection (not applicable with JSON storage)
- No XSS protection (desktop app)

**Potential Improvements:**
```java
private boolean isValidEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
}

private boolean isValidPhoneNumber(String phone) {
    return phone.matches("^\\+?[1-9]\\d{1,14}$");
}
```

---

## System Interconnections Summary

### Data Flow Diagram

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   FXML Views    │    │  MainController │    │   Data Models   │
│                 │◄──►│                 │◄──►│                 │
│ • Forms         │    │ • Event Handlers│    │ • Tourist       │
│ • Tables        │    │ • Validation    │    │ • Guide         │
│ • Charts        │    │ • Business Logic│    │ • Attraction    │
└─────────────────┘    └──────���──────────┘    │ • Booking       │
                                │              └─────────────────┘
                                │                       │
                                ▼                       │
┌─────────────────┐    ┌─────────────────┐             │
│ WeatherService  │    │   DataManager   │◄────────────┘
│                 │    │                 │
│ • API Calls     │    │ • JSON I/O      │
│ • Data Parsing  │    │ • File Management│
│ • Safety Warnings│   │ • Report Export │
└─────────────────┘    └─────────────────┘
         │                       │
         ▼                       ▼
┌─────────────────┐    ┌─────────────────┐
│OpenWeatherMap   │    │  File System    │
│     API         │    │                 │
│                 │    │ • data/*.json   │
│ • Real-time     │    │ • reports/*.txt │
│   Weather Data  │    │                 │
└─────────────────┘    └─────────────────┘
```

This comprehensive system demonstrates enterprise-level Java application development with proper separation of concerns, robust error handling, and integration with external services, all tailored specifically for Nepal's tourism industry requirements.
