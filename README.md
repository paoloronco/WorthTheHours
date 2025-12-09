# WorthTheHours
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/paoloronco/WorthTheHours)

WorthTheHours is a simple yet powerful Android application designed to help you reframe the cost of items by translating their monetary value into the hours of work required to afford them. By inputting your salary details, you can add a list of items and instantly see the time investment each one represents.

## Features

- **Salary Calculation**: Set your net hourly wage directly, or calculate it based on your monthly net salary and total working hours per month. Your salary data is stored securely using Android's EncryptedSharedPreferences.
- **Item Management**: Easily add items you're considering buying, specifying their name and price.
- **Work-Time Conversion**: The app automatically displays how many hours and minutes of work are needed to pay for each item on your list, based on your configured wage.
- **Persistent List**: Your item list is saved locally using Room, so your data is always available when you open the app.
- **Clean, Modern UI**: Built entirely with Jetpack Compose and Material 3 for a smooth and intuitive user experience.

## Screens

### Salary Settings
The first time you open the app, you'll be directed to the Salary Settings screen. Here you can either input your net hourly wage or calculate it from your monthly salary and work hours. This screen can be revisited at any time from the main list.

### Item List
The main screen displays all your saved items. Each item card shows its name, price, and the calculated work time needed to purchase it. You can delete items directly from this list or use the floating action button to add a new one.

### Add Item
A simple form to add a new item by providing its name and price.

## Tech Stack & Architecture

This project is built using modern Android development tools and follows the MVVM (Model-View-ViewModel) architecture.

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for declarative UI development.
- **Architecture**: MVVM
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) for managing dependencies.
- **Asynchronous Operations**: Kotlin Coroutines and Flow for managing background threads and data streams.
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for navigating between screen composables.
- **Data Persistence**:
    - [Room](https://developer.android.com/training/data-storage/room) for storing the list of items in a local SQLite database.
    - [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences) for securely storing the user's salary information.
    - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for storing user preferences, such as whether the initial setup is complete.

## Project Structure
- `data/`: Contains repositories and local data sources (Room DAO, UserPreferencesRepository, EncryptedSharedPreferences wrapper).
- `di/`: Hilt dependency injection modules.
- `model/`: Data models, such as the `Item` entity.
- `ui/`: Jetpack Compose screens, the navigation graph, and theming.
- `viewmodel/`: ViewModels responsible for UI logic and state management for each screen.

## How to Build

To build and run the project, follow these steps:
1. Clone the repository:
   ```bash
   git clone https://github.com/paoloronco/WorthTheHours.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync the project dependencies.
4. Run the app on an Android emulator or a physical device.