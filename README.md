# WorthTheHours
<p style="white-space:nowrap;">
  <img src="https://storage.googleapis.com/github-worththehours/appicon.png" width="100" style="vertical-align:middle;">
  <span style="display:inline-block; font-size:1.35em; font-weight:600; vertical-align:middle; margin-left:10px;">
    <a href="https://play.google.com/store/apps/details?id=com.paoloronco.worththehours&hl=it">▶️ Available on Google Play</a>
  </span>
</p>

**WorthTheHours** is a native Android app that helps you rethink spending by converting prices into **hours of work**.

Instead of asking *“Can I afford this?”*, the app helps you ask a more meaningful question:

> **“How many hours of my life does this cost?”**

By visualizing expenses in terms of working time, WorthTheHours encourages conscious, mindful purchasing decisions.

---

## 📱 App Screens

<p align="center">
  <img src="https://storage.googleapis.com/github-worththehours/Phone-1a-white.png" width="200">
  <img src="https://storage.googleapis.com/github-worththehours/Phone-2-white.png" width="200">
  <img src="https://storage.googleapis.com/github-worththehours/Phone-3.png" width="200">
  <img src="https://storage.googleapis.com/github-worththehours/Phone-4a.png" width="200">
</p>

---

## ✨ Key Features

- **Flexible Salary Setup**
  - Enter your net **hourly wage** directly  
  - Or calculate it from **monthly net salary + working hours**
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

---

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

---

## 🗂 Project Structure

