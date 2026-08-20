package com.weather.dashboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.dashboard.model.WeatherData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Service for fetching weather data from OpenWeatherMap API.
 * Handles HTTP requests, JSON parsing, and error handling.
 */
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String UNITS = "metric"; // Celsius

    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Fetch weather data for a specific city.
     *
     * @param cityName Name of the city
     * @return WeatherData object containing weather information
     * @throws WeatherServiceException if the API call fails
     */
    public WeatherData getWeatherByCity(String cityName) throws WeatherServiceException {
        try {
            logger.info("Fetching weather data for city: {}", cityName);
            String url = String.format("%s?q=%s&appid=%s&units=%s",
                    BASE_URL, cityName, apiKey, UNITS);

            WeatherData weatherData = makeApiRequest(url);
            logger.info("Successfully fetched weather for {}", cityName);
            return weatherData;
        } catch (Exception e) {
            logger.error("Failed to fetch weather data for city: {}", cityName, e);
            throw new WeatherServiceException("Failed to fetch weather for " + cityName, e);
        }
    }

    /**
     * Fetch weather data by geographic coordinates.
     *
     * @param latitude  Latitude of the location
     * @param longitude Longitude of the location
     * @return WeatherData object containing weather information
     * @throws WeatherServiceException if the API call fails
     */
    public WeatherData getWeatherByCoordinates(double latitude, double longitude) throws WeatherServiceException {
        try {
            logger.info("Fetching weather data for coordinates: lat={}, lon={}", latitude, longitude);
            String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=%s",
                    BASE_URL, latitude, longitude, apiKey, UNITS);

            WeatherData weatherData = makeApiRequest(url);
            logger.info("Successfully fetched weather for coordinates");
            return weatherData;
        } catch (Exception e) {
            logger.error("Failed to fetch weather data for coordinates", e);
            throw new WeatherServiceException("Failed to fetch weather for coordinates", e);
        }
    }

    /**
     * Make HTTP request to the weather API.
     *
     * @param url The API endpoint URL
     * @return Parsed WeatherData object
     * @throws Exception if the request fails or API returns an error
     */
    private WeatherData makeApiRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new WeatherServiceException(
                    String.format("API returned status code %d: %s",
                            response.statusCode(), response.body())
            );
        }

        return objectMapper.readValue(response.body(), WeatherData.class);
    }

    /**
     * Custom exception for weather service errors.
     */
    public static class WeatherServiceException extends Exception {
        public WeatherServiceException(String message) {
            super(message);
        }

        public WeatherServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
