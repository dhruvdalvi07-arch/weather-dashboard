# Weather Dashboard

A professional Java application that fetches real-time weather data from the OpenWeatherMap API and displays it in an intuitive GUI dashboard.

## Features

- **Real-time Weather Data**: Fetch current weather information for any city worldwide
- **Multiple Search Methods**: Search by city name or geographic coordinates
- **Comprehensive Weather Information**:
  - Current temperature and "feels like" temperature
  - Weather condition and description
  - Humidity and pressure levels
  - Wind speed and direction
  - Min/Max temperatures
  - Cloud coverage percentage
  - Sunrise and sunset times
  
- **Professional GUI**: Clean Swing-based interface with organized information display
- **Error Handling**: Robust exception handling and user-friendly error messages
- **Logging**: Complete logging using SLF4J for debugging and monitoring
- **Unit Tests**: Comprehensive test coverage

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- OpenWeatherMap API Key (free tier available at https://openweathermap.org/api)

## Installation

### Option 1: Clone from GitHub
```bash
git clone https://github.com/dhruvdalvi07-arch/weather-dashboard.git
cd weather-dashboard
```

### Option 2: Import into VS Code
1. Clone or download the repository
2. Open VS Code
3. Install the Extension Pack for Java
4. Open the project folder in VS Code
5. VS Code will automatically detect the Maven project
6. Dependencies will be downloaded automatically

## Setup

### 1. Get OpenWeatherMap API Key

1. Visit https://openweathermap.org/api
2. Sign up for a free account
3. Generate an API key from your account dashboard

### 2. Set Environment Variable

**Windows (Command Prompt):**
```bash
set WEATHER_API_KEY=your_api_key_here
```

**Windows (PowerShell):**
```powershell
$env:WEATHER_API_KEY="your_api_key_here"
```

**Linux/macOS:**
```bash
export WEATHER_API_KEY="your_api_key_here"
```

**VS Code (settings):**
Create a `.env` file in the project root:
```
WEATHER_API_KEY=your_api_key_here
```

### 3. Build the Project

**Using Maven:**
```bash
mvn clean package
```

**Using VS Code:**
- Open Command Palette (Ctrl+Shift+P)
- Search for "Maven: Execute Commands"
- Select "clean package"

## Running the Application

### Option 1: From VS Code
1. Open `WeatherDashboard.java`
2. Click "Run" above the `main` method
3. Or use Command Palette: "Java: Run"

### Option 2: From Terminal
```bash
mvn exec:java -Dexec.mainClass="com.weather.dashboard.WeatherDashboard"
```

### Option 3: From JAR file
```bash
java -jar target/weather-dashboard-1.0.0.jar
```

## Usage

1. Launch the application
2. Enter a city name in the search field (e.g., "London", "Paris", "Tokyo")
3. Press Enter or click "Search"
4. View the current weather information displayed in the dashboard

## Project Structure

```
weather-dashboard/
├── src/
│   ├── main/
│   │   └── java/com/weather/dashboard/
│   │       ├── WeatherDashboard.java          # Main entry point
│   │       ├── model/
│   │       │   └── WeatherData.java            # Data models
│   │       ├── service/
│   │       │   └── WeatherService.java         # API service
│   │       └── ui/
│   │           └── DashboardUI.java            # GUI components
│   └── test/
│       └── java/com/weather/dashboard/
│           └── service/
│               └── WeatherServiceTest.java     # Unit tests
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## Dependencies

- **jackson-databind**: 2.15.2 - JSON serialization/deserialization
- **slf4j-api**: 2.0.5 - Logging facade
- **slf4j-simple**: 2.0.5 - Simple logging implementation
- **junit**: 4.13.2 - Unit testing framework
- **mockito-core**: 5.2.0 - Mocking framework

## Configuration

### Environment Variables
- `WEATHER_API_KEY`: Your OpenWeatherMap API key (required)

### Application Settings
- **API Endpoint**: `https://api.openweathermap.org/data/2.5/weather`
- **Units**: Metric (Celsius)
- **Connection Timeout**: 10 seconds
- **Request Timeout**: 10 seconds

## API Reference

### WeatherService

#### getWeatherByCity(String cityName)
Fetches weather data for a specific city.

**Parameters**:
- `cityName` (String): Name of the city

**Returns**: `WeatherData` object containing weather information

**Throws**: `WeatherServiceException` if API call fails

**Example**:
```java
WeatherService service = new WeatherService(apiKey);
WeatherData data = service.getWeatherByCity("Paris");
System.out.println("Temperature: " + data.getMain().getTemperature());
```

#### getWeatherByCoordinates(double latitude, double longitude)
Fetches weather data for geographic coordinates.

**Parameters**:
- `latitude` (double): Latitude of the location
- `longitude` (double): Longitude of the location

**Returns**: `WeatherData` object containing weather information

**Throws**: `WeatherServiceException` if API call fails

## Error Handling

The application includes comprehensive error handling:
- Invalid API keys are caught and reported
- Network timeouts are handled gracefully
- Invalid city names return user-friendly error messages
- All errors are logged for debugging purposes

## Testing

Run tests with:
```bash
mvn test
```

**Note**: Tests require `WEATHER_API_KEY` environment variable to be set.

### Test Coverage
- `WeatherServiceTest`: Tests API integration and data parsing
- Invalid input handling
- Network error scenarios

## VS Code Extensions Required

1. **Extension Pack for Java** (Microsoft)
   - Includes Language Support for Java (Red Hat)
   - Debugger for Java
   - Test Runner for Java
   - Maven for Java

2. **Optional**: 
   - SonarLint (code quality)
   - Checkstyle (code style)

## Troubleshooting

### Issue: "WEATHER_API_KEY environment variable not set"
**Solution**: Make sure you set the environment variable before running the application.

### Issue: "Failed to fetch weather for [city]"
**Solution**: 
1. Check your API key is valid
2. Check your internet connection
3. Verify the city name is correct

### Issue: Maven dependencies not downloading
**Solution**:
1. Run `mvn clean install`
2. Check internet connection
3. Verify Maven is properly installed

## Future Enhancements

- [ ] 5-day weather forecast
- [ ] Multiple city comparison
- [ ] Weather alerts and notifications
- [ ] Data persistence and history
- [ ] Web UI using Spring Boot
- [ ] Mobile app version
- [ ] Caching mechanism
- [ ] Dark mode theme
- [ ] Weather maps integration

## License

MIT License - see LICENSE file for details

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Support

For issues, questions, or suggestions, please open an issue on GitHub.

## Authors

- Dhruv Dalvi (@dhruvdalvi07-arch)

## Acknowledgments

- OpenWeatherMap for providing the weather API
- Java Swing documentation
- Maven community
- VS Code team for excellent Java support
