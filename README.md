# 📣 Comments Section

A modern Android app built with Jetpack Compose, MVVM, and Hilt for fetching and displaying comments
from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/). Designed to highlight clean
architecture, composable UI best practices, and robust testing.

---

## 🧠 Project Goals

This project is a showcase of:

- Jetpack Compose UI best practices
- MVVM architecture with clean separation of concerns
- Async state management
- Solid test coverage (unit and UI tests)
- Dependency Injection via Hilt
- Offline-first networking with OkHttp cache

---

## 🗂️ Architecture Overview

### 🔁 Clean MVVM Pattern

```
Presentation (UI) → ViewModel → Domain Layer (Repo Interface) → Data Layer (Repo Impl → API)
```

- **UI Layer:** Compose UI, declarative, reactive to state changes
- **ViewModel:** Exposes state, handles business logic using `AsyncState`
- **Domain Layer:** Defines contract (`TypicodeRepository`) and models
- **Data Layer:** Contains `TypicodeRepositoryImpl` and `ApiService` implementation using Retrofit

---

## 🧩 Key Components

### 🧠 `AsyncState<T>`

A sealed class to model all UI states:

```kotlin
sealed class AsyncState<out T> {
    object Idle : AsyncState<Nothing>()
    object Loading : AsyncState<Nothing>()
    data class Success<T>(val data: T) : AsyncState<T>()
    data class Error(val message: String) : AsyncState<Nothing>()
}
```

With helpful extensions like `DuringComposableState()` and `duringState()` for declarative and
imperative handling.

---

### 📱 UI Highlights

#### ✅ Jetpack Compose + Material 3

- `CommentsScreen` reacts to `AsyncState` using `DuringComposableState` for clean and scoped UI
  updates.
- `Card`-based layout with `CommentItem`, including optional profile images (for future use) via
  `AsyncImage` (Coil).
- Light and dark themes powered by custom color schemes and typography:
    - Font families: `Roboto` and `Comfortaa`
    - Structured `Text.kt` file with typography wrappers for consistency (`LabelLarge`,
      `BodyMedium`, etc.)
- Fully previewed states with @Preview:
    - Success
    - Loading
    - Error

#### 🖼️ Example

```kotlin
LabelLarge(text = "Name: ${comment.name}")
BodyMedium(text = "Body: ${comment.body}")
```

---

### 🔌 DI + Networking

#### Dependency Injection

Using Hilt (`@HiltViewModel`, `@AndroidEntryPoint`, `@Module`) with `AppModule.kt` providing:

- OkHttpClient with offline-first cache strategy
- Retrofit with Gson converter
- API service and repository

#### Caching Strategy

- Online: fresh data cached for 60 seconds
- Offline: serve up to 7-day-old cache using `Cache-Control` headers

---

## ✅ Testing Strategy

### 🧪 Unit Tests

#### 🧠 ViewModel Tests (`CommentsViewModelTest.kt`)

- Tests success and error paths using coroutine test APIs and Mockito
- Verifies loading state is set before result

#### 🛠 Repository Tests (`TypicodeRepositoryTest.kt`)

- Mock Retrofit `Response` for success and failure
- Tests error state when exception is thrown

### 🧪 UI Tests (`CommentsScreenUiTest.kt`)

- Verifies:
    - `Loading` shows spinner
    - `Error` shows correct message
    - `Success` displays comment data correctly
- Uses `createAndroidComposeRule` with test tags

---

## 🧪 Sample Mock Data

Defined in `TestData.kt`:

```kotlin
val mockList = listOf(
    Comment(1, 1, "Name", "email@example.com", "Body")
)
```

---

## 🧵 Notable Design Choices

- ✅ ViewModel state exposed as `mutableStateOf`, encapsulated
- ✅ `LaunchedEffect` in screen to trigger data fetch cleanly
- ✅ `@Preview` with `fakeCommentsListState` to simulate different screen states
- ✅ `Modifier.testTag()` for UI testability
- ✅ Theme switching support and dark mode previews

---

## 🚀 How to Run

Clone and open in Android Studio:

```bash
git clone https://github.com/yourname/comments-viewer.git
```

Make sure to use a recent Android Studio version (Hedgehog or newer) for best experience.

---

## 📸 Screenshots

**Dark Mode**
![image](https://github.com/user-attachments/assets/94c1756c-b969-4eda-98ba-5b0e1924912b)

**Light Mode**
![image](https://github.com/user-attachments/assets/9c5efa0e-b3d6-4d56-b4a6-64faf9524e7d)
