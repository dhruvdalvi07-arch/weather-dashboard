package com.weather.dashboard.ui;

import com.weather.dashboard.model.WeatherData;
import com.weather.dashboard.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Swing-based GUI for the Weather Dashboard.
 * Displays weather information in a user-friendly interface.
 */
public class DashboardUI extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(DashboardUI.class);
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private final WeatherService weatherService;
    private JTextField citySearchField;
    private JLabel cityNameLabel;
    private JLabel temperatureLabel;
    private JLabel descriptionLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel feelsLikeLabel;
    private JLabel pressureLabel;
    private JLabel minMaxTempLabel;
    private JLabel cloudsLabel;
    private JTextArea detailsArea;

    public DashboardUI(WeatherService weatherService) {
        this.weatherService = weatherService;
        initializeUI();
    }

    /**
     * Initialize UI components and layout.
     */
    private void initializeUI() {
        setTitle("Weather Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);
        setLookAndFeel();

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Search panel
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Weather info panel
        JPanel weatherPanel = createWeatherPanel();
        mainPanel.add(weatherPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * Set the look and feel for the application.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Failed to set look and feel", e);
        }
    }

    /**
     * Create the search panel with city input and button.
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Search Location"));
        panel.setBackground(new Color(255, 255, 255));

        JLabel label = new JLabel("City Name:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        
        citySearchField = new JTextField(20);
        citySearchField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchButton.addActionListener(e -> searchWeather());
        citySearchField.addActionListener(e -> searchWeather());

        panel.add(label);
        panel.add(citySearchField);
        panel.add(searchButton);

        return panel;
    }

    /**
     * Create the weather information display panel.
     */
    private JPanel createWeatherPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Left panel - main info
        JPanel mainInfoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainInfoPanel.setBorder(BorderFactory.createTitledBorder("Current Weather"));
        mainInfoPanel.setBackground(new Color(255, 255, 255));

        cityNameLabel = createLabel("City: -");
        temperatureLabel = createLabel("Temperature: - °C");
        descriptionLabel = createLabel("Condition: -");
        feelsLikeLabel = createLabel("Feels Like: - °C");
        minMaxTempLabel = createLabel("Min/Max: - °C / - °C");

        mainInfoPanel.add(cityNameLabel);
        mainInfoPanel.add(temperatureLabel);
        mainInfoPanel.add(descriptionLabel);
        mainInfoPanel.add(feelsLikeLabel);
        mainInfoPanel.add(minMaxTempLabel);

        // Right panel - additional info
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        detailsPanel.setBackground(new Color(255, 255, 255));

        humidityLabel = createLabel("Humidity: - %");
        windSpeedLabel = createLabel("Wind Speed: - m/s");
        pressureLabel = createLabel("Pressure: - hPa");
        cloudsLabel = createLabel("Cloud Coverage: - %");

        detailsPanel.add(humidityLabel);
        detailsPanel.add(windSpeedLabel);
        detailsPanel.add(pressureLabel);
        detailsPanel.add(cloudsLabel);

        // Top info section
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(mainInfoPanel);
        topPanel.add(detailsPanel);

        panel.add(topPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create a styled label.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    /**
     * Handle weather search action.
     */
    private void searchWeather() {
        String cityName = citySearchField.getText().trim();
        if (cityName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city name",
                    "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show loading indicator
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            WeatherData weatherData = weatherService.getWeatherByCity(cityName);
            updateWeatherDisplay(weatherData);
        } catch (WeatherService.WeatherServiceException e) {
            logger.error("Weather search failed", e);
            JOptionPane.showMessageDialog(this,
                    "Failed to fetch weather: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Update the UI with fetched weather data.
     */
    private void updateWeatherDisplay(WeatherData weatherData) {
        if (weatherData == null) {
            return;
        }

        try {
            // Update city name
            String city = weatherData.getCityName();
            if (weatherData.getSystemInfo() != null) {
                city += ", " + weatherData.getSystemInfo().getCountry();
            }
            cityNameLabel.setText("City: " + city);

            // Update temperature
            if (weatherData.getMain() != null) {
                WeatherData.MainWeatherInfo main = weatherData.getMain();
                temperatureLabel.setText(String.format("Temperature: %.1f °C", main.getTemperature()));
                feelsLikeLabel.setText(String.format("Feels Like: %.1f °C", main.getFeelsLike()));
                minMaxTempLabel.setText(String.format("Min/Max: %.1f °C / %.1f °C", 
                        main.getTempMin(), main.getTempMax()));
                humidityLabel.setText(String.format("Humidity: %d %%", main.getHumidity()));
                pressureLabel.setText(String.format("Pressure: %d hPa", main.getPressure()));
            }

            // Update weather description
            if (weatherData.getWeather() != null && !weatherData.getWeather().isEmpty()) {
                WeatherData.Weather weather = weatherData.getWeather().get(0);
                String description = weather.getMain() + " - " + weather.getDescription();
                descriptionLabel.setText("Condition: " + description);
            }

            // Update wind speed
            if (weatherData.getWind() != null) {
                windSpeedLabel.setText(String.format("Wind Speed: %.1f m/s", weatherData.getWind().getSpeed()));
            }

            // Update cloud coverage
            if (weatherData.getClouds() != null) {
                cloudsLabel.setText(String.format("Cloud Coverage: %d %%", 
                        weatherData.getClouds().getCloudPercentage()));
            }

            logger.info("Weather display updated for: {}", weatherData.getCityName());
        } catch (Exception e) {
            logger.error("Failed to update weather display", e);
        }
    }

    /**
     * Initialize the dashboard.
     */
    public void initialize() {
        logger.info("Initializing dashboard UI");
    }

    /**
     * Show the dashboard window.
     */
    @Override
    public void show() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
