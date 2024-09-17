# FluxNews

This app is designed to provide a seamless and user-friendly news reading experience. It features a modern UI built with Jetpack Compose, and it uses MVVM + MVI architecture to ensure separation of concerns and testability. Paging3 is used to efficiently handle large datasets of news articles, and Retrofit is used to fetch news articles from a remote API. Room is used to store news articles locally, and DataStore is used to store user preferences. Dagger Hilt is used to provide dependency injection, making the code more modular and maintainable.

<!-- ![Preview](./img/preview.gif) -->

# Clean Architecture

The core principles of the clean approach can be summarized as followed:

#### Layered Separation:
The application code is organized into distinct layers, each representing a specific concern within the codebase.

#### Strict Dependency Rule:
Each layer is only allowed to interact with layers that are positioned below it in the hierarchy.

#### Generic Lower Layers:
As you move towards the lower layers of the architecture, the code becomes more generic, focusing on defining policies and rules, whereas the upper layers handle specific implementations like database management, user interfaces, and local user managers.

<img name="Architecture" width="60%" src="./_img/baesuii_architecture.png"/>

## Technologies used
|                                                                                                                                                                                                                    |                                                                                      |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------- |
| <img name="Figma" height="50" src="https://user-images.githubusercontent.com/25181517/189715289-df3ee512-6eca-463f-a0f4-c10d94a06b2f.png" href="https://figma.com/">                                               | Figma - Designing software Web, Android, iOS and other prototypes                    |
| <img name="Kotlin" height="50" src="https://user-images.githubusercontent.com/25181517/185062810-7ee0c3d2-17f2-4a98-9d8a-a9576947692b.png" href="https://kotlinlang.org/">                                         | Kotlin - Official language for Android development, known for its concise syntax     | 
| <img name="Jetpack Compose" height="50" src="https://developer.android.com/static/images/spot-icons/jetpack-compose.svg" href="https://developer.android.com/jetpack/compose">                                     | Jetpack Compose - A modern toolkit for building native Android UIs                   |
|                                                                                                                                                                                                                    |                                                                                      |
| <img name="Architecture" height="50" src="https://developers.google.com/static/profile/badges/playlists/android/android-architecture/badge.svg" href="https://developer.android.com/topic/libraries/architecture"> | **Architecture Components**                                                          | 
| UI Layer                                                                                                                                                                                                           | [Paging3](https://kotlinlang.org/) - Efficiently loads data in chunks. <br>[LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holders for UI updates <br>[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Retains UI data across configuration changes <br>[Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - Simplifies app navigation <br>[Room](https://developer.android.google.cn/jetpack/androidx/releases/room) -  Abstraction layer over SQLite for easier database access |
| Data Layer                                                                                                                                                                                                         | [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Async key-value and typed data storage with Kotlin coroutines |
|                                                                                                                                                                                                                    |                                                                                      |
| <img name="Architecture" height="50" src="https://www.iconpacks.net/icons/2/free-injection-icon-3675-thumb.png" href="https://developer.android.com/training/dependency-injection">                                | **Dependency Injection**                                                             | 
|                                                                                                                                                                                                                    | [Dagger](https://dagger.dev/) - A compile-time DI framework <br>[Hilt](https://dagger.dev/hilt) - Simplifies using Dagger in Android    |  
|                                                                                                                                                                                                                    |                                                                                      |     
|<img name="Architecture" height="50" src="https://www.cdnlogo.com/logos/a/15/android.svg">                                                                                                                          | Other Dependencies                                                                   |
|                                                                                                                                                                                                                    | [Retrofit](https://square.github.io/retrofit/) - Android's and Java's type-safe HTTP client   |
