# Elixir Gym - Setup Documentation

## Dependency Injection (Koin)

### Structure
- **NetworkModule** (`di/NetworkModule.kt`): Contains the Ktor HTTP client configuration
- **AppModule** (`di/AppModule.kt`): For app-level dependencies (ViewModels, Repositories, etc.)
- **KoinInitializer** (`di/KoinInitializer.kt`): Initializes Koin with all modules

### Usage
Koin is automatically initialized in each platform's entry point:
- Android: `MainActivity.onCreate()`
- iOS: `MainViewController()`
- JVM Desktop: `main()`
- Web: `main()`

To inject dependencies in your code:
```kotlin
// In a ViewModel or Screen
class MyViewModel : ViewModel() {
    private val httpClient: HttpClient by inject()
}

// Or get directly
val httpClient = get<HttpClient>()
```

## Ktor Client

The Ktor HTTP client is configured in `NetworkModule.kt` with:
- **ContentNegotiation**: JSON serialization/deserialization
- **Logging**: Request/response logging at INFO level
- **Auth**: Authentication support (to be configured)
- **Default Request**: Base URL and content type

### Configuration
Update the base URL in `NetworkModule.kt`:
```kotlin
defaultRequest {
    url("https://your-api-url.com/")
    contentType(ContentType.Application.Json)
}
```

### Usage Example
```kotlin
class TrainerRepository(private val client: HttpClient) {
    suspend fun getTrainers(): List<Trainer> {
        return client.get("trainers").body()
    }
}
```

## Navigation (Voyager)

### Structure
Navigation is implemented using Voyager with:
- **Navigator**: Main navigation controller
- **SlideTransition**: Animated screen transitions
- **Screen**: Interface for all screens

### Available Screens
1. **HomeScreen**: Main landing page
2. **TrainerListScreen**: Browse available trainers
3. **BookingsScreen**: View user bookings

### Usage
Navigate between screens:
```kotlin
val navigator = LocalNavigator.currentOrThrow

// Push a new screen
navigator.push(TrainerListScreen())

// Pop back
navigator.pop()
```

### Creating New Screens
```kotlin
class MyScreen : Screen {
    @Composable
    override fun Content() {
        // Your UI here
    }
}
```

## Project Structure

```
composeApp/src/commonMain/kotlin/com/elixirgym/elixir/
├── di/
│   ├── NetworkModule.kt      # Ktor client configuration
│   ├── AppModule.kt           # App dependencies
│   └── KoinInitializer.kt     # Koin setup
├── presentation/
│   └── screens/
│       ├── HomeScreen.kt
│       ├── TrainerListScreen.kt
│       └── BookingsScreen.kt
└── App.kt                     # Main app composable
```

## Next Steps

1. **API Integration**: Update the base URL in `NetworkModule.kt` and create data models
2. **ViewModels**: Add ViewModels to `AppModule.kt` for business logic
3. **Repositories**: Create repositories for data access
4. **Database**: Configure SQLDelight for local storage
5. **Authentication**: Implement auth flow and configure Ktor Auth plugin

## Dependencies

All required dependencies are already added in `build.gradle.kts`:
- Koin (DI)
- Ktor (Networking)
- Voyager (Navigation)
- Kotlin Serialization
- SQLDelight (Database)
- Coil/Kamel (Image loading)
