# Mini Logbook

A single-screen Android application for tracking blood glucose (BG) readings, built as a technical assessment.

---

## Features

- Select blood glucose unit: **mmol/L** or **mg/dL**
- Enter a BG value via numeric keyboard with real-time validation
- Save readings and track a running **average** displayed at the top
- View previously entered readings in a scrollable list
- **Unit switching** converts all values and labels instantly, including the current input
- Values are **persisted** via Room database across app restarts
- Conversion factor: `1 mmol/L = 18.0182 mg/dL`
---

## Architecture

Clean Architecture with an MVI presentation layer.

```
app/
└── src/main/java/com/alexdev/minilogbook/
    ├── data/
    │   ├── local/          # Room: Entity, DAO, AppDatabase
    │   ├── mapper/         # Mapper: Mappers to domain and to entity models
    │   └── repository/     # BgReadingRepositoryImpl
    ├── domain/
    │   ├── model/          # BgEntry, BgUnit
    │   ├── repository/     # BgReadingRepository (interface)
    │   ├── usecase/        # GetAllReadingsUseCase, SaveBgReadingUseCase
    │   └── ConvertionUtils # Extention functions to convert from or to
    ├── presentation/
    │   ├── components/
    │   │   ├── atoms/      # InputErrorText, UnitRadioButton
    │   │   ├── molecules/  # BgValueTextField, EntryItem, UnitSelector
    │   │   └── organisms/  # AverageHeader, EntryList, BgInputRow
    │   ├── utils/
    │   │   └── BgValueFormatter # Formating units
    │   ├── LogbookUiEvent.kt
    │   ├── LogbookScreen.kt
    │   ├── LogbookUiState.kt
    │   └── LogbookViewModel.kt
    ├── di/
    │   ├── DatabaseModule.kt
    │   ├── MiniLogbookApplication.kt
    │   └── RepositoryModule.kt
    └── MainActivity.kt
```

### Key architectural decisions

**Single source of truth in mmol/L** — all values are stored in mmol/L regardless of the selected unit. Conversion is a presentation-only concern, which simplifies persistence, average calculation, and unit switching significantly.

**MVI with a single `StateFlow<UiState>`** — the ViewModel exposes one immutable state object. The UI sends `LogbookIntent` sealed class instances and never mutates state directly. No `SharedFlow` or `Channel` was needed for this scope.

**Extension functions over a use case class for conversions** — `ConvertBgValueUseCase` was intentionally not wrapped in a class. The conversion logic is three lines of pure math with no dependencies, making top-level extension functions (`Double.toMgDl()`, `Double.toMmol()`, `Double.convertTo()`) the idiomatic Kotlin choice.

**Atomic Design for UI components** — components are organised into atoms, molecules, and organisms. For a single-screen app this is acknowledged over-engineering, but it demonstrates awareness of scalable component architecture. The flat `components/` alternative was considered and deliberately rejected to show the pattern.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVI |
| DI | Hilt |
| Persistence | Room |
| Async | Coroutines + Flow |
| Unit tests | JUnit 4 + MockK + Turbine |
| UI tests | Compose Testing (API ≤ 33) |
| Screenshot tests | Paparazzi 2.0.0-alpha05 + JUnit 5 |

---

## Validation Rules

Blood glucose values are validated against clinically meaningful bounds:

| Unit | Min | Max |
|---|---|---|
| mmol/L | 2.8 | 33.3 |
| mg/dL | ~50.5 | ~599.7 |

- **Minimum (2.8 mmol/L)** — threshold for severe hypoglycaemia; values below this are a medical emergency and outside the realistic range of a self-monitored reading.
- **Maximum (33.3 mmol/L)** — clinical threshold for Hyperosmolar Hyperglycaemic State (HHS); the most extreme diabetic emergency a person managing glucose at home would realistically log.
  The bounds are defined once in mmol/L and derived for mg/dL via the conversion factor, keeping the domain model as the single source of truth.

---

## Testing

### Unit tests (`src/test/`)

- **`ConvertBgValueUseCaseTest`** — pure conversion logic, no mocks
- **`LogbookViewModelTest`** — MVI state transitions tested with Turbine; `ConvertBgValueUseCase` used as real implementation (not mocked) since it has no dependencies
  Covered scenarios:
- Unit conversion updates input value correctly
- Save clears input and calls use case
- Negative / below-minimum value sets error
- Above-maximum value sets error
- Error message reflects current unit after unit switch
### Compose UI tests (`src/androidTest/`)

Instrumented tests running on API ≤ 33 (Espresso has a known incompatibility with `InputManager` on API 34+ that is unresolved upstream).

Covered per component:
- `AverageHeaderTest` — mmol/L, mg/dL, empty state
- `BgInputRowTest` — save enabled/disabled, error display, save intent emission
- `BgValueTextFieldTest` — idle, error state, value change callback
- `InputErrorTextTest` — visible / hidden
- `UnitRadioButtonTest` — selected / unselected states, click callback
- `UnitSelectorTest` — all units rendered, correct intent emitted on click
- `ReadingItemTest` — mmol/L and mg/dL display
- `LogbookContentTest` — full screen integration: empty state, unit selection, input validation, average display, readings list
### Screenshot tests — Paparazzi (`src/test/`)

Paparazzi 2.0.0-alpha05 with JUnit 5 (`setup()`/`teardown()` hooks).

```bash
# Record images
./gradlew app:recordPaparazziDebug

# Verify images (CI)
./gradlew app:verifyPaparazziDebug
```

Golden images are committed under `src/test/snapshots/`.

> **Note on Paparazzi version:** `1.3.5` (last stable) is incompatible with Gradle 9.x / Kotlin 2.x. `2.0.0-alpha05` is the only version supporting this toolchain. The JUnit 5 API (`setup()`/`teardown()`) is required as the JUnit 4 `@get:Rule` approach does not trigger snapshot recording in this version.

---

## Setup

```bash
git clone <repo>
cd MiniLogbook
./gradlew assembleDebug
```

No API keys or environment variables required.

Minimum SDK: **26** | Target SDK: **35**

---

## What I would add with more time

- **Delete / edit readings** — swipe-to-delete on list items
- **Pagination** — `LazyColumn` with paging for large reading histories
- **Chart** — line chart of readings over time using Vico or MPAndroidChart
- **Dark theme preview** — Paparazzi snapshot with `DeviceConfig` dark mode flag
- **Robolectric** — migrate Compose UI tests to Robolectric to remove the API 34+ Espresso limitation and enable CI without an emulator
- **CI pipeline** — GitHub Actions workflow running unit tests + Paparazzi verify on every PR
- **Accessibility** — `contentDescription` audit and TalkBack testing
