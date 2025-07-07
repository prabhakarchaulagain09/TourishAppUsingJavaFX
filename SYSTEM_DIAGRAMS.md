# Nepal Tourism Management System - Complete Diagram Documentation

## 1. Enhanced Class Diagram with Complete Relationships

```mermaid
classDiagram
    %% Main Application
    class TourismApp {
        +start(Stage primaryStage)
        +main(String[] args)
    }

    %% Model Classes
    class Tourist {
        -String id
        -String name
        -String nationality
        -String contact
        -String emergencyContact
        -String email
        -LocalDate registrationDate
        +Tourist()
        +Tourist(String id, String name, String nationality, String contact, String emergencyContact, String email)
        +getId() String
        +setId(String id)
        +getName() String
        +setName(String name)
        +getNationality() String
        +setNationality(String nationality)
        +getContact() String
        +setContact(String contact)
        +getEmergencyContact() String
        +setEmergencyContact(String emergencyContact)
        +getEmail() String
        +setEmail(String email)
        +getRegistrationDate() LocalDate
        +setRegistrationDate(LocalDate registrationDate)
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }

    class Guide {
        -String id
        -String name
        -List~String~ languages
        -int experienceYears
        -String contact
        -String specialization
        -double rating
        -boolean available
        +Guide()
        +Guide(String id, String name, List~String~ languages, int experienceYears, String contact, String specialization)
        +getId() String
        +setId(String id)
        +getName() String
        +setName(String name)
        +getLanguages() List~String~
        +setLanguages(List~String~ languages)
        +getExperienceYears() int
        +setExperienceYears(int experienceYears)
        +getContact() String
        +setContact(String contact)
        +getSpecialization() String
        +setSpecialization(String specialization)
        +getRating() double
        +setRating(double rating)
        +isAvailable() boolean
        +setAvailable(boolean available)
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }

    class Attraction {
        -String id
        -String name
        -String type
        -String location
        -String difficulty
        -int altitude
        -String description
        -double price
        -boolean monsoonRestricted
        +Attraction()
        +Attraction(String id, String name, String type, String location, String difficulty, int altitude, String description, double price)
        +getId() String
        +setId(String id)
        +getName() String
        +setName(String name)
        +getType() String
        +setType(String type)
        +getLocation() String
        +setLocation(String location)
        +getDifficulty() String
        +setDifficulty(String difficulty)
        +getAltitude() int
        +setAltitude(int altitude)
        +getDescription() String
        +setDescription(String description)
        +getPrice() double
        +setPrice(double price)
        +isMonsoonRestricted() boolean
        +setMonsoonRestricted(boolean monsoonRestricted)
        -updateMonsoonRestriction()
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }

    class Booking {
        -String id
        -Tourist tourist
        -Guide guide
        -Attraction attraction
        -LocalDate bookingDate
        -LocalDate trekDate
        -String status
        -double totalPrice
        -boolean festivalDiscount
        -String weatherInfo
        -String emergencyNotes
        +Booking()
        +Booking(String id, Tourist tourist, Guide guide, Attraction attraction, LocalDate trekDate)
        +getId() String
        +setId(String id)
        +getTourist() Tourist
        +setTourist(Tourist tourist)
        +getGuide() Guide
        +setGuide(Guide guide)
        +getAttraction() Attraction
        +setAttraction(Attraction attraction)
        +getBookingDate() LocalDate
        +setBookingDate(LocalDate bookingDate)
        +getTrekDate() LocalDate
        +setTrekDate(LocalDate trekDate)
        +getStatus() String
        +setStatus(String status)
        +getTotalPrice() double
        +setTotalPrice(double totalPrice)
        +isFestivalDiscount() boolean
        +setFestivalDiscount(boolean festivalDiscount)
        +getWeatherInfo() String
        +setWeatherInfo(String weatherInfo)
        +getEmergencyNotes() String
        +setEmergencyNotes(String emergencyNotes)
        -calculateTotalPrice()
        -isFestivalSeason() boolean
        +toString() String
        +equals(Object obj) boolean
        +hashCode() int
    }

    %% Controller
    class MainController {
        -ObservableList~Tourist~ tourists
        -ObservableList~Guide~ guides
        -ObservableList~Attraction~ attractions
        -ObservableList~Booking~ bookings
        -List~String~ emergencyLogs
        -boolean isNepali
        -WeatherService weatherService
        -DataManager dataManager
        -Map~String, String~ currentLanguageStrings
        +initialize(URL location, ResourceBundle resources)
        -initializeTableColumns()
        -initializeComboBoxes()
        -setupEventHandlers()
        -loadInitialData()
        +addTourist()
        +updateTourist()
        +deleteTourist()
        +addGuide()
        +updateGuide()
        +deleteGuide()
        +addAttraction()
        +updateAttraction()
        +deleteAttraction()
        +addBooking()
        +updateBooking()
        +deleteBooking()
        +updateWeatherInfo()
        -isMonsoonRestricted() boolean
        -updateBookingPrice()
        +reportEmergency()
        -updateAnalytics()
        +toggleLanguage()
        +exportReport()
        -showAlert(String title, String message)
        -refreshAllTables()
        -validateBookingForm() boolean
        -clearAllForms()
    }

    %% Service Classes
    class WeatherService {
        -String API_KEY
        -String BASE_URL
        -Map~String, String~ locationMappings
        +getWeatherForLocation(String location) WeatherInfo
        +getWeatherForAttraction(String attractionName, String location) WeatherInfo
        -generateWeatherWarning(String temperature, String description, String windSpeed) String
        -mapAttractionToLocation(String attractionName) String
        -buildApiUrl(String location) String
        -parseWeatherResponse(String jsonResponse) WeatherInfo
    }

    class WeatherInfo {
        -String temperature
        -String description
        -String humidity
        -String windSpeed
        -String visibility
        -String warning
        +WeatherInfo(String temperature, String description, String humidity, String windSpeed, String visibility, String warning)
        +getTemperature() String
        +getDescription() String
        +getHumidity() String
        +getWindSpeed() String
        +getVisibility() String
        +getWarning() String
        +toString() String
        +hasWarning() boolean
        +isSevereWeather() boolean
    }

    %% Utility Classes
    class DataManager {
        -String DATA_DIR
        -String TOURISTS_FILE
        -String GUIDES_FILE
        -String ATTRACTIONS_FILE
        -String BOOKINGS_FILE
        -String REPORTS_DIR
        -Gson gson
        -LocalDateAdapter dateAdapter
        +saveTourists(ObservableList~Tourist~ tourists)
        +loadTourists() List~Tourist~
        +saveGuides(ObservableList~Guide~ guides)
        +loadGuides() List~Guide~
        +saveAttractions(ObservableList~Attraction~ attractions)
        +loadAttractions() List~Attraction~
        +saveBookings(ObservableList~Booking~ bookings)
        +loadBookings() List~Booking~
        +exportReport(ObservableList~Tourist~, ObservableList~Guide~, ObservableList~Attraction~, ObservableList~Booking~)
        -createDirectories()
        -initializeGson() Gson
        +backupData()
        +restoreData()
    }

    class LocalDateAdapter {
        +serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) JsonElement
        +deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) LocalDate
    }

    class LanguageStrings {
        -Map~String, String~ englishStrings
        -Map~String, String~ nepaliStrings
        +getEnglishStrings() Map~String, String~
        +getNepaliStrings() Map~String, String~
        +getString(String key, boolean isNepali) String
        -initializeEnglishStrings() Map~String, String~
        -initializeNepaliStrings() Map~String, String~
    }

    %% External / JavaFX
    class ObservableList~T~ {
        <<interface>>
        +add(T item) boolean
        +remove(T item) boolean
        +clear()
        +size() int
        +get(int index) T
    }

    class Gson {
        +toJson(Object src) String
        +fromJson(String json, Class~T~ classOfT) T
        +fromJson(String json, Type typeOfT) T
    }

    class LocalDate {
        +now() LocalDate
        +of(int year, int month, int day) LocalDate
        +toString() String
        +isAfter(LocalDate other) boolean
        +isBefore(LocalDate other) boolean
    }

    class TableView~T~ {
        <<JavaFX>>
        +setItems(ObservableList~T~ items)
        +getSelectionModel() SelectionModel~T~
    }

    class ComboBox~T~ {
        <<JavaFX>>
        +setItems(ObservableList~T~ items)
        +getValue() T
        +setValue(T value)
    }

    %% Relationships
    TourismApp ||--|| MainController
    MainController ||--o{ Tourist
    MainController ||--o{ Guide
    MainController ||--o{ Attraction
    MainController ||--o{ Booking
    Booking *--|| Tourist
    Booking *--|| Guide
    Booking *--|| Attraction
    Tourist ||--o{ Booking
    Guide ||--o{ Booking
    Attraction ||--o{ Booking
    MainController ||--|| WeatherService
    MainController ||--|| DataManager
    MainController ||--|| LanguageStrings
    WeatherService ||--o{ WeatherInfo
    DataManager ||--o{ Tourist
    DataManager ||--o{ Guide
    DataManager ||--o{ Attraction
    DataManager ||--o{ Booking
    DataManager ||--|| LocalDateAdapter
    DataManager ||--|| Gson
    MainController ||--|| ObservableList~Tourist~
    MainController ||--|| ObservableList~Guide~
    MainController ||--|| ObservableList~Attraction~
    MainController ||--|| ObservableList~Booking~
    MainController ||--o{ TableView~Tourist~
    MainController ||--o{ ComboBox~Tourist~
    MainController ||--o{ ComboBox~Guide~
    MainController ||--o{ ComboBox~Attraction~
    Tourist ||--|| LocalDate
    Booking ||--|| LocalDate
    ObservableList~Tourist~ ||--o{ Tourist
    ObservableList~Guide~ ||--o{ Guide
    ObservableList~Attraction~ ||--o{ Attraction
    ObservableList~Booking~ ||--o{ Booking
    Guide ||--o{ String
    MainController ||--o{ String
```


