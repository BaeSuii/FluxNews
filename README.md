# Overview

FluxNews is an Android app using Kotlin and Jetpack Compose and provides real-time news updates. It's made for limited amount of time but it has the following features:

- **Onboarding Experience**: Seamless and informative experience for new users
- **Home with Breaking News & Worldwide News**: Able to view the latest breaking news from all over the world
- **Explore News by Categories**: Easily browse news by categories such as health, technology, business and more
- **Search for Articles**: Quickly find articles related to your interests
- **Bookmark Articles**: Tap once and save the articles to read later
- **Real-Time Weather**: Get weather updates in real time based on your specified timezone
- **Article Details**: Read brief content of an article, as well as share and read more by directing to the article link.
- **Personalized Settings**: Give yourself a nickname and toggle dark mode.

# **Preview**
<img src="_src/prev_onboarding.gif" width="32%"> <img src="_src/prev_home.gif" width="32%"> <img src="_src/prev_category.gif" width="32%">
<img src="_src/prev_search.gif" width="32%"> <img src="_src/prev_bookmark.gif" width="32%"> <img src="_src/prev_settings.gif" width="32%"> 

# **Screenshots**
<img src="_src/night/ss_home.png" width="32%"> <img src="_src/night/ss_explore.png" width="32%"> <img src="_src/night/ss_search.png" width="32%"> 
<img src="_src/night/ss_bookmark.png" width="32%"> <img src="_src/night/ss_article.png" width="32%"> <img src="_src/night/ss_settings.png" width="32%"> 

# Architecture
- Presentation: Responsible for the UI and input management
- Domain: Contains the business logic, including the use cases and repository interfaces
- Data: Responsible for database operations, network requests and caching.

<img name="Architecture" width="100%" src="./_src/baesuii_architecture.png"/>

# **Technologies Used**
|                                                                                                                    |                                                                                            |                                                                                     |
|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------|
| <img src="https://user-images.githubusercontent.com/25181517/185062810-7ee0c3d2-17f2-4a98-9d8a-a9576947692b.png" height="24"> | [**Kotlin**](https://kotlinlang.org/)                                           | Official language for Android development, known for its concise syntax             |
| <img src="https://developer.android.com/static/images/spot-icons/jetpack-compose.svg" height="24">                 | [**Jetpack Compose**](https://developer.android.com/jetpack/compose)                       | A modern toolkit for building native Android UIs                                    |
| <img src="https://upload.wikimedia.org/wikipedia/commons/3/33/Figma-logo.svg" height="24">                         | [**Figma**](https://www.figma.com/)                                                        | A collaborative design tool for creating UI/UX designs                              |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Paging Library**](https://developer.android.com/topic/libraries/architecture/paging)    | Efficiently loads data in chunks (paging)                                           |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**LiveData**](https://developer.android.com/topic/libraries/architecture/livedata)        | An observable data holder for UI updates                                            |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**ViewModel**](https://developer.android.com/topic/libraries/architecture/viewmodel)      | Retains UI data across configuration changes                                        |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Navigation Components**](https://developer.android.com/guide/navigation/navigation-getting-started) | Simplifies app navigation                                               |
| <img src="https://img.stackshare.io/service/2856/retrofit-logo.png" height="24">                                   | [**Retrofit**](https://square.github.io/retrofit/)                                         | A type-safe HTTP client for making API requests                                     |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**Room**](https://developer.android.com/training/data-storage/room)                       | A persistence library for local database management                                 |
| <img src="https://developer.android.com/images/logos/android.svg" height="24">                                     | [**DataStore**](https://developer.android.com/topic/libraries/architecture/datastore)      | Async key-value and typed data storage with Kotlin coroutines                       |
| <img src="https://avatars.githubusercontent.com/u/52722434?s=200&v=4" height="24">                                 | [**Coil**](https://coil-kt.github.io/coil/)                                                | An image loading library for Android                                                |
| <img src="https://newsapi.org/images/n-logo-border.png" height="24">                                               | [**NewsAPI**](https://newsapi.org/)                                                        | A third-party API for fetching news articles                                        |
| <img src="https://avatars.githubusercontent.com/u/1743227?s=200&v=4" height="24">                                  | [**OpenWeatherMap**](https://openweathermap.org/)                                          | A third-party API for fetching real-time weather data                               |
| <img src="https://www.iconpacks.net/icons/2/free-injection-icon-3675-thumb.png" height="24">                       | [**Dagger**](https://dagger.dev/)                                                          | A compile-time dependency injection framework for Android                           |
| <img src="https://www.iconpacks.net/icons/2/free-injection-icon-3675-thumb.png" height="24">                       | [**Hilt**](https://dagger.dev/hilt/)                                                       | A dependency injection library that simplifies injecting dependencies in Android apps |
| <img src="https://square.github.io/okhttp/assets/images/icon-square.png" height="24">                              | [**OkHttp**](https://square.github.io/okhttp/)                                             | A networking library for HTTP requests                                              |
| <img src="https://junit.org/junit5/assets/img/junit5-logo.png" height="24">                                        | [**JUnit**](https://junit.org/junit5/)                                                     | A testing framework for writing unit tests                                          |
| <img src="https://avatars.githubusercontent.com/u/34787540?s=280&v=4" height="24">                                 | [**MockK**](https://mockk.io/)                                                             | A mocking framework for unit testing Kotlin code                                    |
| <img src="https://avatars.githubusercontent.com/u/49219790?s=48&v=4" height="24">                                  | [**Turbine**](https://github.com/cashapp/turbine)                                          | A testing library for Kotlin Flows                                                  |

