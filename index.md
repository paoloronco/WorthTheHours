# WorthTheHours

**WorthTheHours** is a native Android app that helps you rethink spending by converting prices into **hours of work**.

Instead of asking *“Can I afford this?”*, the app helps you ask a more meaningful question:

> **“How many hours of my life does this cost?”**

By visualizing expenses in terms of working time, WorthTheHours encourages conscious, mindful purchasing decisions.

*** 

## ✨ Key Features

- **Flexible Salary Setup**
  - Enter your net **hourly wage** directly  
  - or calculate it from **monthly net salary + working hours**
  - Salary data is stored **securely and locally**, encrypted on the device

- **Item Tracking**
  - Add items with a name and price
  - Instantly see how many **hours and minutes of work** each item represents

- **Automatic Time Conversion**
  - Prices are converted into working time using your configured hourly wage
  - Results are displayed in a clear, human-friendly format

- **Local & Private**
  - No accounts
  - No cloud sync
  - No network access
  - All data stays on your device

- **Modern UI**
  - Built entirely with **Jetpack Compose**
  - Uses **Material 3** for a clean, minimal, modern look
  - Supports light and dark themes

*** 

## 📱 App Screens

### Salary Settings
Set your net hourly wage or calculate it from your monthly salary and working hours.  
This can be changed at any time from the main screen.

### Item List
The main screen shows your saved items.  
Each item displays:
- Name
- Price
- Equivalent working time (hours + minutes)

Items can be removed with a simple action.

### Add Item
Quickly add a new item by entering its name and price.

***

## 🧱 Tech Stack & Architecture

WorthTheHours is built using modern Android best practices and follows a **clean MVVM architecture**.

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM
- **Dependency Injection**: Hilt
- **Async & State**: Kotlin Coroutines + Flow
- **Navigation**: Navigation Compose
- **Local Persistence**:
  - **Room** for storing items
  - **EncryptedSharedPreferences** for securely storing salary data
  - **DataStore** for lightweight user preferences

## Project Structure
- `data/`: Contains repositories and local data sources (Room DAO, UserPreferencesRepository, EncryptedSharedPreferences wrapper).
- `di/`: Hilt dependency injection modules.
- `model/`: Data models, such as the `Item` entity.
- `ui/`: Jetpack Compose screens, the navigation graph, and theming.
- `viewmodel/`: ViewModels responsible for UI logic and state management for each screen.

*** 

## 🗂 Project Structure
```
app
 ├─ manifests
 │   └─ AndroidManifest.xml
 ├─ kotlin+java
 │   └─ com.paoloronco.worththehours
 │        ├─ data
 │        │   ├─ local
 │        │   └─ repository
 │        ├─ di
 │        ├─ model
 │        ├─ ui
 │        │   ├─ screens
 │        │   ├─ theme
 │        │   └─ NavGraph.kt
 │        ├─ viewmodel
 │        ├─ MainActivity.kt
 │        └─ WorthTheHoursApplication.kt
 ├─ java (generated)
 └─ res

```

*** 

## 🛠 How to Build & Run

1. Clone the repository:
   ``` bash
   git clone https://github.com/paoloronco/WorthTheHours.git
  ```

2. Open the project in **Android Studio**

3. Let Gradle sync complete

4. Run the app on an emulator or physical device

> Note: `local.properties` is generated automatically by Android Studio and is not required in the repository.

***

## 🔒 Privacy First

WorthTheHours is designed with privacy in mind:

* No backend servers
* No analytics tracking
* No personal data leaves your device

Your time — and your data — are yours.

***