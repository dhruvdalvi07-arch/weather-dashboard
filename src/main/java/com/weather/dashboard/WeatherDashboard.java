package com.weather.dashboard;

import com.weather.dashboard.model.WeatherData;
import com.weather.dashboard.service.WeatherService;
import com.weather.dashboard.ui.DashboardUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Weather Dashboard application.
 * Orchestrates the weather service and UI components.
 */
public class WeatherDashboard {
    private static final Logger logger = LoggerFactory.getLogger(WeatherDashboard.class);
    private final WeatherService weatherService;
    private final DashboardUI dashboardUI;

    public WeatherDashboard(WeatherService weatherService, DashboardUI dashboardUI) {
        this.weatherService = weatherService;
        this.dashboardUI = dashboardUI;
    }

    public void start() {
        logger.info("Starting Weather Dashboard Application");
        dashboardUI.initialize();
        dashboardUI.show();
    }

    public static void main(String[] args) {
        try {
            String apiKey = System.getenv("WEATHER_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                logger.error("WEATHER_API_KEY environment variable not set");
                System.err.println("Please set WEATHER_API_KEY environment variable");
                System.exit(1);
            }

            WeatherService weatherService = new WeatherService(apiKey);
            DashboardUI dashboardUI = new DashboardUI(weatherService);
            WeatherDashboard dashboard = new WeatherDashboard(weatherService, dashboardUI);
            dashboard.start();
        } catch (Exception e) {
            logger.error("Failed to start Weather Dashboard", e);
            System.exit(1);
        }
    }
}
