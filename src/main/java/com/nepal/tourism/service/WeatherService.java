package com.nepal.tourism.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String API_KEY = "Put-Key-Here";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    
    public static class WeatherInfo {
        private String temperature;
        private String description;
        private String humidity;
        private String windSpeed;
        private String visibility;
        private String warning;
        
        public WeatherInfo(String temperature, String description, String humidity, String windSpeed, String visibility, String warning) {
            this.temperature = temperature;
            this.description = description;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
            this.visibility = visibility;
            this.warning = warning;
        }
        
        // Getters
        public String getTemperature() { return temperature; }
        public String getDescription() { return description; }
        public String getHumidity() { return humidity; }
        public String getWindSpeed() { return windSpeed; }
        public String getVisibility() { return visibility; }
        public String getWarning() { return warning; }
        
        @Override
        public String toString() {
            return String.format("Temperature: %s°C\nCondition: %s\nHumidity: %s%%\nWind: %s km/h\nVisibility: %s km%s",
                    temperature, description, humidity, windSpeed, visibility, 
                    warning.isEmpty() ? "" : "\n⚠️ " + warning);
        }
    }
    
    public static WeatherInfo getWeatherForLocation(String location) {
        try {
            String urlString = BASE_URL + "?q=" + location + ",NP&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject main = jsonObject.getAsJsonObject("main");
            JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
            JsonObject wind = jsonObject.getAsJsonObject("wind");
            
            String temperature = String.valueOf(Math.round(main.get("temp").getAsDouble()));
            String description = weather.get("description").getAsString();
            String humidity = String.valueOf(main.get("humidity").getAsInt());
            String windSpeed = String.valueOf(Math.round(wind.get("speed").getAsDouble() * 3.6)); // Convert m/s to km/h
            String visibility = jsonObject.has("visibility") ? 
                String.valueOf(jsonObject.get("visibility").getAsInt() / 1000) : "N/A";
            
            String warning = generateWeatherWarning(temperature, description, windSpeed);
            
            return new WeatherInfo(temperature, description, humidity, windSpeed, visibility, warning);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new WeatherInfo("N/A", "Unable to fetch weather data", "N/A", "N/A", "N/A", 
                "Weather service unavailable. Please check your internet connection.");
        }
    }
    
    private static String generateWeatherWarning(String temperature, String description, String windSpeed) {
        StringBuilder warning = new StringBuilder();
        
        try {
            int temp = Integer.parseInt(temperature);
            int wind = Integer.parseInt(windSpeed);
            
            if (temp < 0) {
                warning.append("Extreme cold conditions. Risk of frostbite. ");
            } else if (temp < 5) {
                warning.append("Very cold conditions. Proper winter gear required. ");
            }
            
            if (wind > 50) {
                warning.append("High wind speeds. Dangerous for high-altitude activities. ");
            } else if (wind > 30) {
                warning.append("Strong winds. Exercise caution during outdoor activities. ");
            }
            
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
    
    public static WeatherInfo getWeatherForAttraction(String attractionName, String location) {
        // Map attraction names to specific locations for better weather accuracy
        String weatherLocation = location;
        
        if (attractionName.toLowerCase().contains("everest")) {
            weatherLocation = "Namche Bazaar";
        } else if (attractionName.toLowerCase().contains("annapurna")) {
            weatherLocation = "Pokhara";
        } else if (attractionName.toLowerCase().contains("langtang")) {
            weatherLocation = "Dhunche";
        }
        
        return getWeatherForLocation(weatherLocation);
    }
}
