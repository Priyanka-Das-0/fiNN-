# fiNN-
# Stock Lookup App

## Overview

The fiNN is a simple Android application that allows users to check the current stock prices, percentage changes, and company names by entering a stock symbol. It leverages data from the [TwelveData API](https://twelvedata.com) and displays real-time stock information on the app’s interface.

The app is built using **Kotlin** and incorporates **MVVM (Model-View-ViewModel)** architecture for a clean separation of concerns, making it easier to manage UI-related data in a lifecycle-conscious way.

## Features

- Enter a stock symbol to retrieve:

  - Current stock price
  - Percentage change in stock price
  - Company name
- Visual indicator (color) based on the percentage change:
  - Green for positive changes
  - Red for negative changes
- Smooth UI updates using **ViewModels** and **LiveData**
- Progress indicator while the app is fetching stock data

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** pattern to keep the logic separated from the UI components. It uses:
- **ViewModel**: Handles all business logic and acts as a bridge between the repository (for fetching stock data) and the view (the UI).
- **LiveData**: Observable data holder that updates the UI automatically when data changes.
- **Retrofit**: For making network calls to the TwelveData API.

## Screenshots

![ss](https://github.com/user-attachments/assets/92554ba9-324b-4fa9-ab95-525cd116fa11)



## Dependencies

- **Retrofit**: For network requests to the TwelveData API
- **Gson**: For JSON serialization and deserialization
- **LiveData & ViewModel**: Part of Android Jetpack for handling lifecycle-aware components

## API

The app fetches stock information using the [TwelveData API](https://twelvedata.com). You’ll need to sign up for an API key from TwelveData to get access to real-time stock data.

## ViewModels

### 1. **StockViewModel**

`StockViewModel` is responsible for managing stock-related data and providing it to the view. It fetches the stock's quote information such as:
- Stock symbol
- Percentage change in the stock price
- Company name

#### Key Methods:
- **fetchStockQuote(symbol: String, apiKey: String, apiService: TwelveDataApiService)**: 
  - Fetches stock information from the API and updates the LiveData objects.
- **LiveData Variables**:
  - `stockQuote`: Holds the stock quote data (`StockQuoteResponse`) and is observed by the activity.
  - `isLoading`: Tracks whether the data is being fetched and shows/hides the progress bar.
  - `errorMessage`: Handles error messages and passes them to the UI.

### 2. **PriceViewModel**

`PriceViewModel` manages the stock price separately. It is specifically responsible for fetching the current stock price of a given symbol.

#### Key Methods:
- **fetchPrice(symbol: String, apiKey: String, apiService: OneDataApiService)**:
  - Fetches the stock price from the API and updates the stock price in `LiveData`.

#### LiveData Variables:
- `stockPrice`: Holds the stock's current price and updates the `priceTextView` in the UI.

## How It Works

1. The user inputs a stock symbol (e.g., `AAPL` for Apple) and presses the **Update** button.
2. The `StockViewModel` and `PriceViewModel` are notified and make an API call to fetch the stock information (company name, price, percent change).
3. The ViewModel updates the `LiveData` objects, which are observed by the activity.
4. The UI is automatically updated with the fetched data, and the progress bar is hidden when loading is complete.

## Setup

### Prerequisites

- Android Studio
- An API key from [TwelveData API](https://twelvedata.com)

### Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/Priyanka-Das-0/fiNN-.git
    ```

2. Open the project in **Android Studio**.
3. Replace the `apiKey` in `MainActivity.kt` with your own TwelveData API key.
4. Run the app on an emulator or physical device.

### Project Structure

- `MainActivity.kt`: Main activity that contains UI and handles user interaction.
- `StockViewModel.kt`: ViewModel that handles fetching the stock quote (company name, percent change).
- `PriceViewModel.kt`: ViewModel that handles fetching the stock price.
- `TwelveDataApiService.kt`: Interface for Retrofit API calls.
- `StockQuoteResponse.kt`: Data model for stock quote response from TwelveData API.
- `activity_main.xml`: Layout file for the app’s UI.

