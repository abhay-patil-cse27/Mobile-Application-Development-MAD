# Font Controller App

A simple Android application designed to demonstrate basic UI components and the **Data-Adapter-View** pattern. This app allows users to control the appearance of text dynamically using various input controls.

## Features & Components

1.  **Dynamic Text Preview (TextView & EditText)**:
    *   Uses a `TextWatcher` to update the `TextView` in real-time as you type in the `EditText`.
2.  **Enable/Disable Control (CheckBox)**:
    *   An "Enable Editing" checkbox that toggles the `isEnabled` state of the input field.
3.  **Font Size Selection (Spinner)**:
    *   Demonstrates the `View-Adapter-Data` pattern using a dropdown list.
    *   **Data**: Defined in `strings.xml` as a string array.
    *   **Adapter**: `ArrayAdapter` used to bridge the data and the view.
    *   **View**: `Spinner` UI element.
4.  **Color Selection (RadioGroup & RadioButtons)**:
    *   Changes the text color of the preview to Red, Green, or Blue.
5.  **Text Style Selection (CheckBoxes)**:
    *   Toggles **Bold** and *Italic* styles independently.
6.  **Font Family Selection (ListView)**:
    *   Another example of the `View-Adapter-Data` pattern.
    *   Allows selecting between Default, Monospace, Serif, and Sans Serif fonts.

## Project Structure

*   **`MainActivity.kt`**: Contains the logic for initializing views and setting up listeners. It manages the "main memory" data dynamically.
*   **`activity_main.xml`**: Defines the layout of the application using a `LinearLayout` inside a `ScrollView`.
*   **`strings.xml`**: Centralized location for all static text and data arrays (Font Sizes and Font Families).

## Simulated Data Source Architecture

This project simulates a professional data-driven architecture by bifurcating the project into three distinct parts:

1.  **External Data Source (The Simulation)**: The `strings.xml` file acts as our static database. Instead of hardcoding list items in Kotlin, we store them as resources. This simulates fetching data from an external source like a Database or an API.
2.  **The Adapter (The Bridge)**: The `ArrayAdapter` in `MainActivity.kt` serves as the middleman. It doesn't care about the source of the data; its only job is to format the data for the View.
3.  **The View (The UI)**: The `Spinner` and `ListView` are empty containers that only display data once the Adapter provides it.

## Explainations

*   **Separation of Concerns**: We have separated the **Data** (strings.xml), the **UI** (activity_main.xml), and the **Logic** (MainActivity.kt).
*   **Adapter Pattern**: Explain how the `ArrayAdapter` bridges the data source to the UI components. This is a fundamental concept in Android development for handling lists.
*   **Dynamic vs. Static**: Mention that while the font lists are **static** (from XML), the text in the preview and its styling are **dynamic** (managed in the app's "main memory" during runtime).
*   **Event Listeners**: We use various listeners (`TextWatcher`, `onItemSelectedListener`, etc.) to react to user input instantly without needing a submit button.
