# Photo Search Application

## Project Description
This project is an Android application that demonstrates the use of modern Android development practices, including clean architecture, modularization, and a variety of popular libraries and technologies. 
The application search photos data from a remote API and displays it in a user-friendly interface.

## Tech Stack
- Kotlin
- Android Jetpack (ViewModel, LiveData, Room, Paging)
- Retrofit
- Hilt (Dagger)
- Coroutines
- Jetpack Compose
- Coil
- Gson
- OkHttp
- Mockito
- Trust
- Jacoco report test coverage
- Setup CI/CD with Github Actions

## Clean Architecture
The project follows the principles of clean architecture, which separates the code into different layers with distinct responsibilities. The main layers are:
- **Presentation Layer**: Contains the UI components and the ViewModels that interact with the UI.
- **Domain Layer**: Contains the business logic and use cases.
- **Data Layer**: Contains the data sources, repositories, and data models.
- **Testing Layer**: Contains the unit tests for the code.

## Modularization
The project is organized into modules to promote separation of concerns and improve build times. The main modules are:
- **presentation**: The main application module that contains the UI and presentation logic.
- **data**: The data module that contains the data sources, repositories, and data models.
- **domain**: The domain module that contains the business logic and use cases.
- **testing**: A separate module for unit test.

## Project Structure
```
android-test/
├── presentation/
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── res/
│   │   └── test/
├── data/
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── res/
│   │   └── test/
├── domain/
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── res/
│   │   └── test/
├── testing/
│   ├── build.gradle.kts
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```_

## App environment
Build variants:
- **development**: The development build variant is used for local development and testing. It includes all the necessary dependencies and configurations for development.
- **production**: The production build variant is used for the release version of the application. It includes optimizations and configurations for production use.

## Run unit test
To run the unit tests, you can use the following command:
```bash
./gradlew test
```
This will run all the unit tests in the project and generate a test report.
