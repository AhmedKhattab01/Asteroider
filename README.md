# Asteroider

Asteroider is an Android application that displays data for upcoming near earth objects and daily new planetary photos with explanations provided by NASA. This app is intended as a project for my portfolio and is available on GitHub.

## Features

- Displays data for upcoming near earth objects using NASA API
- Displays daily new planetary photo with explanation provided by NASA
- Uses the following technologies:
  - Room: for local data storage
  - Retrofit: for making API requests to NASA's Near Earth Object Web Service and Astronomy Picture of the Day API
  - OkHttp: for HTTP client interactions
  - Coroutines: for asynchronous programming
  - ViewModel and StateFlow: for UI and data management
  - Navigation Component: for navigating between screens
  - Single Activity Multiple Fragments: for a consistent and modular UI
  - Repository Pattern and MVVM: for separation of concerns and maintainability
  - Hilt: for dependency injection
  - Work Manager: for scheduling and managing background tasks

## Mockup Images

Below are the mockup images for the Asteroider app:

<img src="/mocks/home.png" alt="Mockup Image 1" height="200">
![Mockup Image 1]()
![Mockup Image 2](/mocks/planetary_details.png)

## Installation

To install and run this app, follow these steps:

1. Clone the repository onto your local machine.
2. Open the project in Android Studio.
3. Build and run the project on an emulator or connected device.

## Usage

Once the app is installed, you can use it to view data for upcoming near earth objects and daily new planetary photos. The app's main screen displays a list of upcoming near earth objects, and you can click on an item in the list to view more details about that object. The planetary screen displays a new photo each day, along with an explanation provided by NASA.

## Contributing

If you would like to contribute to this project, feel free to submit a pull request. Before submitting a pull request, please make sure that your changes are in line with the project's goals and coding conventions.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

This app uses data provided by NASA's Near Earth Object Web Service and Astronomy Picture of the Day API.
