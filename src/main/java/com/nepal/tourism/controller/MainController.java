package com.nepal.tourism.controller;

import com.nepal.tourism.model.*;
import com.nepal.tourism.service.WeatherService;
import com.nepal.tourism.util.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    public Button applyDiscountButton;
    public CheckBox festivalDiscountCheck;
    // Tab Pane
    @FXML private TabPane mainTabPane;
    
    // Tourist Tab
    @FXML private TextField touristIdField;
    @FXML private TextField touristNameField;
    @FXML private TextField touristNationalityField;
    @FXML private TextField touristContactField;
    @FXML private TextField touristEmergencyField;
    @FXML private TextField touristEmailField;
    @FXML private TableView<Tourist> touristTable;
    @FXML private TableColumn<Tourist, String> touristIdColumn;
    @FXML private TableColumn<Tourist, String> touristNameColumn;
    @FXML private TableColumn<Tourist, String> touristNationalityColumn;
    @FXML private TableColumn<Tourist, String> touristContactColumn;
    
    // Guide Tab
    @FXML private TextField guideIdField;
    @FXML private TextField guideNameField;
    @FXML private TextField guideLanguagesField;
    @FXML private TextField guideExperienceField;
    @FXML private TextField guideContactField;
    @FXML private TextField guideSpecializationField;
    @FXML private TableView<Guide> guideTable;
    @FXML private TableColumn<Guide, String> guideIdColumn;
    @FXML private TableColumn<Guide, String> guideNameColumn;
    @FXML private TableColumn<Guide, Integer> guideExperienceColumn;
    @FXML private TableColumn<Guide, String> guideSpecializationColumn;
    
    // Attraction Tab
    @FXML private TextField attractionIdField;
    @FXML private TextField attractionNameField;
    @FXML private ComboBox<String> attractionTypeCombo;
    @FXML private TextField attractionLocationField;
    @FXML private ComboBox<String> attractionDifficultyCombo;
    @FXML private TextField attractionAltitudeField;
    @FXML private TextArea attractionDescriptionArea;
    @FXML private TextField attractionPriceField;
    @FXML private TableView<Attraction> attractionTable;
    @FXML private TableColumn<Attraction, String> attractionIdColumn;
    @FXML private TableColumn<Attraction, String> attractionNameColumn;
    @FXML private TableColumn<Attraction, String> attractionTypeColumn;
    @FXML private TableColumn<Attraction, String> attractionLocationColumn;
    @FXML private TableColumn<Attraction, String> attractionDifficultyColumn;
    
    // Booking Tab
    @FXML private TextField bookingIdField;
    @FXML private ComboBox<Tourist> bookingTouristCombo;
    @FXML private ComboBox<Guide> bookingGuideCombo;
    @FXML private ComboBox<Attraction> bookingAttractionCombo;
    @FXML private DatePicker bookingDatePicker;
    @FXML private ComboBox<String> bookingStatusCombo;
    @FXML private Label bookingPriceLabel;
    @FXML private Label festivalDiscountLabel;
    @FXML private TextArea weatherInfoArea;
    @FXML private Button getWeatherButton;
    @FXML private TableView<Booking> bookingTable;
    @FXML private TableColumn<Booking, String> bookingIdColumn;
    @FXML private TableColumn<Booking, String> bookingTouristColumn;
    @FXML private TableColumn<Booking, String> bookingAttractionColumn;
    @FXML private TableColumn<Booking, String> bookingStatusColumn;
    @FXML private TableColumn<Booking, Double> bookingPriceColumn;
    
    // Analytics Tab
    @FXML private PieChart nationalityChart;
    @FXML private BarChart<String, Number> attractionChart;
    @FXML private Label totalTouristsLabel;
    @FXML private Label totalBookingsLabel;
    @FXML private Label totalRevenueLabel;
    @FXML private VBox analyticsContainer;
    
    // Emergency Tab
    @FXML private ComboBox<Booking> emergencyBookingCombo;
    @FXML private TextArea emergencyNotesArea;
    @FXML private ListView<String> emergencyLogList;
    
    // Language Toggle
    @FXML private Button languageToggleButton;
    private boolean isNepali = false;
    
    // Data Collections
    private ObservableList<Tourist> tourists = FXCollections.observableArrayList();
    private ObservableList<Guide> guides = FXCollections.observableArrayList();
    private ObservableList<Attraction> attractions = FXCollections.observableArrayList();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private List<String> emergencyLogs = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        initializeComboBoxes();
        loadSampleData();
        loadDataFromFiles();
        updateAnalytics();
        setupEventHandlers();
    }
    
    private void initializeTableColumns() {
        // Tourist table columns
        touristIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        touristNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        touristNationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        touristContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
        // Guide table columns
        guideIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        guideNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        guideExperienceColumn.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
        guideSpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        
        // Attraction table columns
        attractionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        attractionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        attractionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        attractionLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        attractionDifficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        
        // Booking table columns
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookingTouristColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTourist().getName()));
        bookingAttractionColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAttraction().getName()));
        bookingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        bookingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        // Set table data
        touristTable.setItems(tourists);
        guideTable.setItems(guides);
        attractionTable.setItems(attractions);
        bookingTable.setItems(bookings);
    }
    
    private void initializeComboBoxes() {
        attractionTypeCombo.setItems(FXCollections.observableArrayList(
            "Trek", "Heritage", "Adventure", "Cultural", "Wildlife", "Pilgrimage"));
        
        attractionDifficultyCombo.setItems(FXCollections.observableArrayList(
            "Easy", "Moderate", "Hard", "Extreme"));
        
        bookingStatusCombo.setItems(FXCollections.observableArrayList(
            "Pending", "Confirmed", "Cancelled", "Completed"));
        
        bookingTouristCombo.setItems(tourists);
        bookingGuideCombo.setItems(guides);
        bookingAttractionCombo.setItems(attractions);
        emergencyBookingCombo.setItems(bookings);
    }
    
    private void setupEventHandlers() {
        // Weather button handler
        getWeatherButton.setOnAction(e -> updateWeatherInfo());
        
        // Booking attraction selection handler
        bookingAttractionCombo.setOnAction(e -> {
            updateBookingPrice();
            updateWeatherInfo();
        });
        
        // Date picker handler
        bookingDatePicker.setOnAction(e -> updateBookingPrice());
        
        // Table selection handlers
        touristTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateTouristFields(newSelection);
        });
        
        guideTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateGuideFields(newSelection);
        });
        
        attractionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateAttractionFields(newSelection);
        });
        
        bookingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateBookingFields(newSelection);
        });
    }
    
    // Tourist CRUD Operations
    @FXML
    private void addTourist() {
        try {
            Tourist tourist = new Tourist(
                touristIdField.getText(),
                touristNameField.getText(),
                touristNationalityField.getText(),
                touristContactField.getText(),
                touristEmergencyField.getText(),
                touristEmailField.getText()
            );
            
            tourists.add(tourist);
            clearTouristFields();
            DataManager.saveTourists(tourists);
            updateAnalytics();
            showAlert("Success", "Tourist added successfully!");
            
        } catch (Exception e) {
            showAlert("Error", "Failed to add tourist: " + e.getMessage());
        }
    }
    
    @FXML
    private void updateTourist() {
        Tourist selected = touristTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setId(touristIdField.getText());
            selected.setName(touristNameField.getText());
            selected.setNationality(touristNationalityField.getText());
            selected.setContact(touristContactField.getText());
            selected.setEmergencyContact(touristEmergencyField.getText());
            selected.setEmail(touristEmailField.getText());
            
            touristTable.refresh();
            DataManager.saveTourists(tourists);
            updateAnalytics();
            showAlert("Success", "Tourist updated successfully!");
        }
    }
    
    @FXML
    private void deleteTourist() {
        Tourist selected = touristTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tourists.remove(selected);
            clearTouristFields();
            DataManager.saveTourists(tourists);
            updateAnalytics();
            showAlert("Success", "Tourist deleted successfully!");
        }
    }
    
    // Guide CRUD Operations
    @FXML
    private void addGuide() {
        try {
            List<String> languages = Arrays.asList(guideLanguagesField.getText().split(","));
            Guide guide = new Guide(
                guideIdField.getText(),
                guideNameField.getText(),
                languages,
                Integer.parseInt(guideExperienceField.getText()),
                guideContactField.getText(),
                guideSpecializationField.getText()
            );
            
            guides.add(guide);
            clearGuideFields();
            DataManager.saveGuides(guides);
            showAlert("Success", "Guide added successfully!");
            
        } catch (Exception e) {
            showAlert("Error", "Failed to add guide: " + e.getMessage());
        }
    }
    
    @FXML
    private void updateGuide() {
        Guide selected = guideTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setId(guideIdField.getText());
            selected.setName(guideNameField.getText());
            selected.setLanguages(Arrays.asList(guideLanguagesField.getText().split(",")));
            selected.setExperienceYears(Integer.parseInt(guideExperienceField.getText()));
            selected.setContact(guideContactField.getText());
            selected.setSpecialization(guideSpecializationField.getText());
            
            guideTable.refresh();
            DataManager.saveGuides(guides);
            showAlert("Success", "Guide updated successfully!");
        }
    }
    
    @FXML
    private void deleteGuide() {
        Guide selected = guideTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            guides.remove(selected);
            clearGuideFields();
            DataManager.saveGuides(guides);
            showAlert("Success", "Guide deleted successfully!");
        }
    }
    
    // Attraction CRUD Operations
    @FXML
    private void addAttraction() {
        try {
            Attraction attraction = new Attraction(
                attractionIdField.getText(),
                attractionNameField.getText(),
                attractionTypeCombo.getValue(),
                attractionLocationField.getText(),
                attractionDifficultyCombo.getValue(),
                Integer.parseInt(attractionAltitudeField.getText()),
                attractionDescriptionArea.getText(),
                Double.parseDouble(attractionPriceField.getText())
            );
            
            attractions.add(attraction);
            clearAttractionFields();
            DataManager.saveAttractions(attractions);
            updateAnalytics();
            showAlert("Success", "Attraction added successfully!");
            
        } catch (Exception e) {
            showAlert("Error", "Failed to add attraction: " + e.getMessage());
        }
    }
    
    @FXML
    private void updateAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setId(attractionIdField.getText());
            selected.setName(attractionNameField.getText());
            selected.setType(attractionTypeCombo.getValue());
            selected.setLocation(attractionLocationField.getText());
            selected.setDifficulty(attractionDifficultyCombo.getValue());
            selected.setAltitude(Integer.parseInt(attractionAltitudeField.getText()));
            selected.setDescription(attractionDescriptionArea.getText());
            selected.setPrice(Double.parseDouble(attractionPriceField.getText()));
            
            attractionTable.refresh();
            DataManager.saveAttractions(attractions);
            updateAnalytics();
            showAlert("Success", "Attraction updated successfully!");
        }
    }
    
    @FXML
    private void deleteAttraction() {
        Attraction selected = attractionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            attractions.remove(selected);
            clearAttractionFields();
            DataManager.saveAttractions(attractions);
            updateAnalytics();
            showAlert("Success", "Attraction deleted successfully!");
        }
    }
    
    // Booking CRUD Operations
    @FXML
    private void addBooking() {
        try {
            if (isMonsoonRestricted()) {
                showAlert("Warning", "This trek is restricted during monsoon season (June-August) for safety reasons!");
                return;
            }
            
            Booking booking = new Booking(
                bookingIdField.getText(),
                bookingTouristCombo.getValue(),
                bookingGuideCombo.getValue(),
                bookingAttractionCombo.getValue(),
                bookingDatePicker.getValue()
            );
            booking.setStatus(bookingStatusCombo.getValue());
            booking.setWeatherInfo(weatherInfoArea.getText());
            
            bookings.add(booking);
            clearBookingFields();
            DataManager.saveBookings(bookings);
            updateAnalytics();
            showAlert("Success", "Booking added successfully!" + 
                (booking.isFestivalDiscount() ? " Festival discount applied!" : ""));
            
        } catch (Exception e) {
            showAlert("Error", "Failed to add booking: " + e.getMessage());
        }
    }
    
    @FXML
    private void updateBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setId(bookingIdField.getText());
            selected.setTourist(bookingTouristCombo.getValue());
            selected.setGuide(bookingGuideCombo.getValue());
            selected.setAttraction(bookingAttractionCombo.getValue());
            selected.setTrekDate(bookingDatePicker.getValue());
            selected.setStatus(bookingStatusCombo.getValue());
            selected.setWeatherInfo(weatherInfoArea.getText());
            
            bookingTable.refresh();
            DataManager.saveBookings(bookings);
            updateAnalytics();
            showAlert("Success", "Booking updated successfully!");
        }
    }
    
    @FXML
    private void deleteBooking() {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bookings.remove(selected);
            clearBookingFields();
            DataManager.saveBookings(bookings);
            updateAnalytics();
            showAlert("Success", "Booking deleted successfully!");
        }
    }
    
    // Weather and Safety Methods
    @FXML
    private void updateWeatherInfo() {
        Attraction selectedAttraction = bookingAttractionCombo.getValue();
        if (selectedAttraction != null) {
            try {
                WeatherService.WeatherInfo weather = WeatherService.getWeatherForAttraction(
                    selectedAttraction.getName(), selectedAttraction.getLocation());
                weatherInfoArea.setText(weather.toString());
                
                // Show altitude sickness warning for high-altitude treks
                if (selectedAttraction.getAltitude() > 3000) {
                    weatherInfoArea.appendText("\n\n🏔️ HIGH ALTITUDE WARNING:\n" +
                        "Risk of altitude sickness above 3000m. Acclimatization recommended.\n" +
                        "Symptoms: Headache, nausea, fatigue, dizziness.\n" +
                        "Seek immediate medical attention if symptoms worsen.");
                }
                
            } catch (Exception e) {
                weatherInfoArea.setText("Unable to fetch weather information: " + e.getMessage());
            }
        }
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
    
    private void updateBookingPrice() {
        Attraction attraction = bookingAttractionCombo.getValue();
        LocalDate date = bookingDatePicker.getValue();
        
        if (attraction != null) {
            double price = attraction.getPrice();
            boolean festivalDiscount = false;
            
            if (date != null) {
                int month = date.getMonthValue();
                if (month == 9 || month == 10 || month == 11) { // Festival season
                    price *= 0.85; // 15% discount
                    festivalDiscount = true;
                }
            }
            
            bookingPriceLabel.setText(String.format("$%.2f", price));
            festivalDiscountLabel.setText(festivalDiscount ? "Festival Discount Applied!" : "");
            festivalDiscountLabel.setVisible(festivalDiscount);
        }
    }
    
    // Emergency Management
    @FXML
    private void reportEmergency() {
        Booking selectedBooking = emergencyBookingCombo.getValue();
        String notes = emergencyNotesArea.getText();
        
        if (selectedBooking != null && !notes.trim().isEmpty()) {
            String emergencyLog = String.format("[%s] EMERGENCY - Booking: %s, Tourist: %s, Notes: %s",
                LocalDate.now().toString(),
                selectedBooking.getId(),
                selectedBooking.getTourist().getName(),
                notes);
            
            emergencyLogs.add(emergencyLog);
            emergencyLogList.getItems().add(emergencyLog);
            
            selectedBooking.setEmergencyNotes(notes);
            emergencyNotesArea.clear();
            
            showAlert("Emergency Reported", "Emergency has been logged and relevant authorities will be notified.");
        }
    }
    
    // Analytics and Reporting
    private void updateAnalytics() {
        updateNationalityChart();
        updateAttractionChart();
        updateStatistics();
    }
    
    private void updateNationalityChart() {
        Map<String, Long> nationalityCount = tourists.stream()
            .collect(Collectors.groupingBy(Tourist::getNationality, Collectors.counting()));
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        nationalityCount.forEach((nationality, count) -> 
            pieChartData.add(new PieChart.Data(nationality, count)));
        
        nationalityChart.setData(pieChartData);
    }
    
    private void updateAttractionChart() {
        Map<String, Long> attractionCount = bookings.stream()
            .collect(Collectors.groupingBy(booking -> booking.getAttraction().getName(), Collectors.counting()));
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Bookings per Attraction");
        
        attractionCount.forEach((attraction, count) -> 
            series.getData().add(new XYChart.Data<>(attraction, count)));
        
        attractionChart.getData().clear();
        attractionChart.getData().add(series);
    }
    
    private void updateStatistics() {
        totalTouristsLabel.setText("Total Tourists: " + tourists.size());
        totalBookingsLabel.setText("Total Bookings: " + bookings.size());
        
        double totalRevenue = bookings.stream()
            .mapToDouble(Booking::getTotalPrice)
            .sum();
        totalRevenueLabel.setText(String.format("Total Revenue: $%.2f", totalRevenue));
    }
    
    // Language Toggle
    @FXML
    public void toggleLanguage() {
        try {
            boolean isCurrentlyNepali = languageToggleButton.getText().equals("English");
            String fxmlFile = isCurrentlyNepali ? "/fxml/MainView.fxml" : "/fxml/MainView_np.fxml";
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = languageToggleButton.getScene();
            scene.setRoot(loader.load());
            
            // Get the new controller instance and initialize it with current data
            MainController newController = loader.getController();
            // Transfer any necessary data to the new controller
            // newController.initializeData(...);
            
        } catch (Exception e) {
            System.err.println("Failed to change language: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Field Population Methods
    private void populateTouristFields(Tourist tourist) {
        touristIdField.setText(tourist.getId());
        touristNameField.setText(tourist.getName());
        touristNationalityField.setText(tourist.getNationality());
        touristContactField.setText(tourist.getContact());
        touristEmergencyField.setText(tourist.getEmergencyContact());
        touristEmailField.setText(tourist.getEmail());
    }
    
    private void populateGuideFields(Guide guide) {
        guideIdField.setText(guide.getId());
        guideNameField.setText(guide.getName());
        guideLanguagesField.setText(String.join(",", guide.getLanguages()));
        guideExperienceField.setText(String.valueOf(guide.getExperienceYears()));
        guideContactField.setText(guide.getContact());
        guideSpecializationField.setText(guide.getSpecialization());
    }
    
    private void populateAttractionFields(Attraction attraction) {
        attractionIdField.setText(attraction.getId());
        attractionNameField.setText(attraction.getName());
        attractionTypeCombo.setValue(attraction.getType());
        attractionLocationField.setText(attraction.getLocation());
        attractionDifficultyCombo.setValue(attraction.getDifficulty());
        attractionAltitudeField.setText(String.valueOf(attraction.getAltitude()));
        attractionDescriptionArea.setText(attraction.getDescription());
        attractionPriceField.setText(String.valueOf(attraction.getPrice()));
    }
    
    private void populateBookingFields(Booking booking) {
        bookingIdField.setText(booking.getId());
        bookingTouristCombo.setValue(booking.getTourist());
        bookingGuideCombo.setValue(booking.getGuide());
        bookingAttractionCombo.setValue(booking.getAttraction());
        bookingDatePicker.setValue(booking.getTrekDate());
        bookingStatusCombo.setValue(booking.getStatus());
        weatherInfoArea.setText(booking.getWeatherInfo());
        bookingPriceLabel.setText(String.format("$%.2f", booking.getTotalPrice()));
        festivalDiscountLabel.setVisible(booking.isFestivalDiscount());
    }
    
    // Clear Field Methods
    private void clearTouristFields() {
        touristIdField.clear();
        touristNameField.clear();
        touristNationalityField.clear();
        touristContactField.clear();
        touristEmergencyField.clear();
        touristEmailField.clear();
    }
    
    private void clearGuideFields() {
        guideIdField.clear();
        guideNameField.clear();
        guideLanguagesField.clear();
        guideExperienceField.clear();
        guideContactField.clear();
        guideSpecializationField.clear();
    }
    
    private void clearAttractionFields() {
        attractionIdField.clear();
        attractionNameField.clear();
        attractionTypeCombo.setValue(null);
        attractionLocationField.clear();
        attractionDifficultyCombo.setValue(null);
        attractionAltitudeField.clear();
        attractionDescriptionArea.clear();
        attractionPriceField.clear();
    }
    
    private void clearBookingFields() {
        bookingIdField.clear();
        bookingTouristCombo.setValue(null);
        bookingGuideCombo.setValue(null);
        bookingAttractionCombo.setValue(null);
        bookingDatePicker.setValue(null);
        bookingStatusCombo.setValue(null);
        weatherInfoArea.clear();
        bookingPriceLabel.setText("$0.00");
        festivalDiscountLabel.setVisible(false);
    }
    
    // Data Loading
    private void loadSampleData() {
        // Sample tourists
        tourists.addAll(Arrays.asList(
            new Tourist("T001", "John Smith", "USA", "+1-555-0123", "+1-555-0124", "john@email.com"),
            new Tourist("T002", "Emma Wilson", "UK", "+44-20-7946-0958", "+44-20-7946-0959", "emma@email.com"),
            new Tourist("T003", "Hiroshi Tanaka", "Japan", "+81-3-1234-5678", "+81-3-1234-5679", "hiroshi@email.com")
        ));
        
        // Sample guides
        guides.addAll(Arrays.asList(
            new Guide("G001", "Pemba Sherpa", Arrays.asList("English", "Nepali", "Tibetan"), 15, "+977-98-1234-5678", "High Altitude Trekking"),
            new Guide("G002", "Ang Dorje", Arrays.asList("English", "Nepali"), 12, "+977-98-2345-6789", "Cultural Tours"),
            new Guide("G003", "Lakpa Sherpa", Arrays.asList("English", "Nepali", "Hindi"), 8, "+977-98-3456-7890", "Adventure Sports")
        ));
        
        // Sample attractions
        attractions.addAll(Arrays.asList(
            new Attraction("A001", "Everest Base Camp Trek", "Trek", "Khumbu", "Extreme", 5364, "World's highest base camp trek", 2500.0),
            new Attraction("A002", "Annapurna Circuit", "Trek", "Annapurna", "Hard", 5416, "Classic Himalayan circuit trek", 1800.0),
            new Attraction("A003", "Kathmandu Durbar Square", "Heritage", "Kathmandu", "Easy", 1400, "UNESCO World Heritage Site", 50.0)
        ));
    }
    
    private void loadDataFromFiles() {
        try {
            List<Tourist> loadedTourists = DataManager.loadTourists();
            if (!loadedTourists.isEmpty()) {
                tourists.clear();
                tourists.addAll(loadedTourists);
            }
            
            List<Guide> loadedGuides = DataManager.loadGuides();
            if (!loadedGuides.isEmpty()) {
                guides.clear();
                guides.addAll(loadedGuides);
            }
            
            List<Attraction> loadedAttractions = DataManager.loadAttractions();
            if (!loadedAttractions.isEmpty()) {
                attractions.clear();
                attractions.addAll(loadedAttractions);
            }
            
            List<Booking> loadedBookings = DataManager.loadBookings();
            if (!loadedBookings.isEmpty()) {
                bookings.clear();
                bookings.addAll(loadedBookings);
            }
            
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    
    // Utility Methods
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void exportReport() {
        try {
            DataManager.exportReport(tourists, guides, attractions, bookings);
            showAlert("Success", "Report exported successfully!");
        } catch (Exception e) {
            showAlert("Error", "Failed to export report: " + e.getMessage());
        }
    }

    public void applyFestivalDiscount(ActionEvent actionEvent) {
        //  DISCOUNT RAEEEEEE??? HEHEHEHEHE PAWXAS
    }

    // Add these helper methods
    private void setIsNepali(boolean nepali) {
        this.isNepali = nepali;
    }

    public void initializeWithData(ObservableList<Tourist> tourists,
                                 ObservableList<Guide> guides,
                                 ObservableList<Attraction> attractions,
                                 ObservableList<Booking> bookings) {
        this.tourists = tourists;
        this.guides = guides;
        this.attractions = attractions;
        this.bookings = bookings;

        initializeTableColumns();
        initializeComboBoxes();
        updateAnalytics();
    }
}
