package com.nepal.tourism.util;

import java.util.HashMap;
import java.util.Map;

public class LanguageStrings {
    private static final Map<String, String> NEPALI = new HashMap<>();
    private static final Map<String, String> ENGLISH = new HashMap<>();
    
    static {
        // Initialize Nepali translations
        NEPALI.put("tourists", "पर्यटकहरू");
        NEPALI.put("guides", "गाइडहरू");
        NEPALI.put("attractions", "आकर्षणहरू");
        NEPALI.put("bookings", "बुकिङहरू");
        NEPALI.put("analytics", "विश्लेषण");
        NEPALI.put("emergency", "आपतकालीन");
        // Add more translations as needed
        
        // Initialize English translations
        ENGLISH.put("tourists", "Tourists");
        ENGLISH.put("guides", "Guides");
        ENGLISH.put("attractions", "Attractions");
        ENGLISH.put("bookings", "Bookings");
        ENGLISH.put("analytics", "Analytics");
        ENGLISH.put("emergency", "Emergency");
        // Add more translations as needed
    }
    
    public static String get(String key, boolean isNepali) {
        return isNepali ? NEPALI.getOrDefault(key, key) : ENGLISH.getOrDefault(key, key);
    }
}