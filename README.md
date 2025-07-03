# Nepal Tourism Management System

A comprehensive JavaFX desktop application designed to manage tourism operations in Nepal, featuring weather integration, festival discounts, safety alerts, and multilingual support. If you are interested to know how this system works
please visit 📖 [TechinicalStuff](/TourishAppUsingJavaFX/TECHNICAL_STUFF.md)


## Features

### Core Functionality
- **Tourist Management**: Add, update, delete, and view tourist information with emergency contacts
- **Guide Management**: Manage tour guides with language skills and specializations
- **Attraction Management**: Catalog trekking routes, heritage sites, and cultural attractions
- **Booking System**: Complete booking management with pricing and status tracking

### Nepal-Specific Features
- **Festival Discounts**: Automatic 15% discount during Dashain, Tihar, and other major festivals
- **Monsoon Restrictions**: Safety alerts and booking restrictions for high-altitude treks during monsoon season
- **Altitude Warnings**: Automatic altitude sickness warnings for treks above 3000m
- **Emergency Management**: Emergency contact system and incident reporting

### Weather Integration
- **Live Weather Data**: Real-time weather information using OpenWeatherMap API
- **Location-Specific Forecasts**: Weather data for specific trekking locations
- **Safety Warnings**: Automatic weather-based safety alerts and recommendations
- **Trek Planning**: Weather information displayed when planning bookings

### Analytics & Reporting
- **Tourist Statistics**: Nationality breakdown and visitor analytics
- **Popular Attractions**: Booking trends and attraction popularity charts
- **Revenue Tracking**: Financial reporting and revenue analysis
- **Data Export**: Export reports to text/CSV format

### Technical Features
- **Data Persistence**: JSON-based file storage system
- **Exception Handling**: Comprehensive error handling and user-friendly messages
- **Multilingual Support**: English/Nepali language toggle
- **Responsive Design**: Nepal-inspired UI with cultural color schemes

## Prerequisites

- Java 24 or higher
- Maven 3.6 or higher
- Internet connection (for weather API)
- JavaFX Runtime
- Minimum display resolution: 1200x800


## Setup Instructions

### 1. Clone or Download the Project
```bash
git clone https://github.com/prabhakarchaulagain09/TourishAppUsingJavaFX.git
cd TourishAppUsingJavaFX
```

### 2. Get Weather API Key
1. Sign up at [OpenWeatherMap](https://openweathermap.org/api)
2. Get your free API key
3. Replace `your_openweather_api_key_here` in `WeatherService.java` with your actual API key

### 3. Build the Project
```bash
mvn clean compile
```

### 4. Run the Application
```bash
mvn javafx:run
```

### 5. Create Executable JAR
```bash
mvn clean package
java -jar target/TourishAppUsingJavaFX-1.0.0.jar
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/nepal/tourism/
│   │       ├── TourismApp.java              # Main application class
│   │       ├── controller/
│   │       │   └── MainController.java      # Main UI controller
│   │       ├── model/                       # Data models
│   │       │   ├── Tourist.java
│   │       │   ├── Guide.java
│   │       │   ├── Attraction.java
│   │       │   └── Booking.java
│   │       ├── service/
│   │       │   └── WeatherService.java      # Weather API integration
│   │       └── util/
│   │           ├── DataManager.java         # File I/O operations
│   │           └── LocalDateAdapter.java    # JSON date serialization
│   └── resources/
│       ├── fxml/
│           └── MainView.fxml               # Main UI layout
│           └── MainView_np.fxml            # Main UI layout in Nepali
│
├── test/
│   └── java/
│       └── com/nepal/tourism/
│           └── BookingTest.java            # JUnit tests
└── pom.xml                                 # Maven configuration
```

## Core Features Details

### 1. Tourist Management
- Navigate to the "Tourists" tab
- Fill in tourist information including emergency contacts
- Use Add/Update/Delete buttons to manage records
- Click on table rows to edit existing tourists

### 2. Guide Management
- Go to "Guides" tab
- Enter guide details including languages (comma-separated)
- Specify experience years and specialization
- Manage guide availability and ratings

### 3. Attraction Management
- Use "Attractions" tab to add trekking routes and sites
- Set difficulty levels and altitude information
- Configure pricing and detailed descriptions
- System automatically sets monsoon restrictions for high-altitude treks

### 4. Booking System
- Navigate to "Bookings" tab
- Select tourist, guide, and attraction from dropdowns
- Choose trek date (system checks for monsoon restrictions)
- Click "Get Weather" to fetch current weather conditions
- System automatically applies festival discounts when applicable
- Monitor altitude warnings for high-altitude destinations

### 5. Weather Integration
- Weather information is automatically fetched when selecting attractions
- Real-time weather data includes temperature, conditions, humidity, and wind
- Safety warnings are generated based on weather conditions
- Weather data is stored with each booking for reference

### 6. Emergency Management
- Use "Emergency" tab to report incidents
- Select affected booking and add detailed notes
- System logs all emergency reports with timestamps
- Emergency contacts are readily available for each tourist

### 7. Analytics
- View "Analytics" tab for comprehensive reports
- Charts show tourist nationality distribution and popular attractions
- Export detailed reports for business analysis
- Track revenue and booking trends

## Weather API Configuration

The application uses OpenWeatherMap API for weather data. To configure:

1. Get API key from [OpenWeatherMap](https://openweathermap.org/api)
2. Open `src/main/java/com/nepal/tourism/service/WeatherService.java`
3. Replace the API_KEY constant:
```java
private static final String API_KEY = "your_actual_api_key_here";
```

## Data Storage

The application uses JSON files for data persistence:
- `data/tourists.json` - Tourist records
- `data/guides.json` - Guide information
- `data/attractions.json` - Attraction details
- `data/bookings.json` - Booking records
- `reports/` - Exported reports directory

## Testing

Run the included JUnit tests:
\`\`\`bash
mvn test
\`\`\`

Tests cover:
- Booking creation and validation
- Festival discount calculations
- Price calculations
- Emergency note handling
- Weather information storage

## Nepal-Specific Features

### Festival Seasons
- **Dashain** (September-October): 15% discount applied
- **Tihar** (October-November): 15% discount applied
- System automatically detects festival periods and applies discounts

### Safety Features
- **Monsoon Restrictions**: June-August restrictions for high-altitude treks
- **Altitude Warnings**: Automatic warnings for destinations above 3000m
- **Weather Alerts**: Real-time weather-based safety recommendations


## Troubleshooting

### Common Issues

1. **Weather API not working**
   - Check internet connection
   - Verify API key is correctly set
   - Ensure API key is active and has remaining quota

2. **Data not persisting**
   - Check file permissions in project directory
   - Ensure `data/` directory exists and is writable

3. **Application won't start**
   - Verify Java 11+ is installed
   - Check JavaFX runtime is available
   - Run `mvn clean compile` to rebuild

4. **Charts not displaying**
   - Ensure sample data is loaded
   - Check that bookings and tourists exist in the system

## Contributing

This project follows standard Java coding conventions:
- Use meaningful variable and method names
- Include comprehensive JavaDoc comments
- Follow OOP principles (encapsulation, inheritance, polymorphism)
- Handle exceptions gracefully
- Write unit tests for new features

## License

This project is developed for educational purposes as part of the Advanced Programming course at Taylor's University.

## Support

For technical support or questions about the application, please refer to the course materials or contact the development team.
