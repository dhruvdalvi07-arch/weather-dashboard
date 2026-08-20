package com.weather.dashboard.service;

import com.weather.dashboard.model.WeatherData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for WeatherService.
 */
public class WeatherServiceTest {
    private WeatherService weatherService;

    @Before
    public void setUp() {
        // Use a test API key - set WEATHER_API_KEY environment variable
        String apiKey = System.getenv("WEATHER_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            weatherService = new WeatherService(apiKey);
        }
    }

    @Test
    public void testGetWeatherByCity() throws WeatherService.WeatherServiceException {
        if (weatherService == null) {
            System.out.println("Skipping test - API key not set");
            return;
        }
        WeatherData weatherData = weatherService.getWeatherByCity("London");
        assertNotNull(weatherData);
        assertNotNull(weatherData.getCityName());
        assertNotNull(weatherData.getMain());
        assertTrue(weatherData.getStatusCode() == 200);
    }

    @Test
    public void testGetWeatherByCoordinates() throws WeatherService.WeatherServiceException {
        if (weatherService == null) {
            System.out.println("Skipping test - API key not set");
            return;
        }
        // Coordinates for London
        WeatherData weatherData = weatherService.getWeatherByCoordinates(51.5074, -0.1278);
        assertNotNull(weatherData);
        assertNotNull(weatherData.getCoordinates());
        assertEquals(51.5074, weatherData.getCoordinates().getLatitude(), 0.1);
    }
}
