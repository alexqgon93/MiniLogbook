# Mini Logbook

A focused Android application for tracking blood glucose (BG) readings locally on device.

---

## Features

- Select blood glucose unit: **mmol/L** or **mg/dL**
- Enter a BG value via numeric keyboard with real-time validation
- Save readings and track a running **average** displayed at the top
- View previously entered readings in a scrollable list grouped by day
- **Unit switching** converts all values and labels instantly, including the current input
- **Status badge** per reading and on the average card (Below target / Within target / Above target / High)
- Values are **persisted** via Room database across app restarts
- Conversion factor: `1 mmol/L = 18.0182 mg/dL`
- **Accessibility** support: TalkBack-ready with content descriptions, semantic roles, and FlowRow layouts for large text sizes
---

## Architecture

Clean Architecture with an MVI presentation layer.

```
├── app/                  # Application shell, Hilt composition root, MainActivity
├── core/
│   ├── domain/           # BgEntry, BgUnit, repository contracts, use cases, conversion
│   ├── database/         # Room entity, DAO, AppDatabase, migrations
│   ├── data/             # Repository implementation, mappers, system time provider
│   ├── ui/               # Theme, design tokens, reusable Compose components
│   └── testing/          # Shared fixtures and test utilities
└── feature/
    └── logbook/          # Logbook MVI state, ViewModel, screen, previews
```

### Key architectural decisions

**Single source of truth in mmol/L** — all values are stored in mmol/L regardless of the selected unit. Conversion is a presentation-only concern, which simplifies persistence, average calculation, and unit switching significantly.

**Bounded history loading** — the repository queries the most recent readings using a configurable limit instead of loading the full table into memory. Paging 3 remains the next step if full archive browsing is added.

**MVI with a single `StateFlow<UiState>`** — the ViewModel exposes one immutable state object. The UI sends `LogbookIntent` sealed class instances and never mutates state directly. No `SharedFlow` or `Channel` was needed for this scope.

**Extension functions over a use case class for conversions** — `ConvertBgValueUseCase` was intentionally not wrapped in a class. The conversion logic is three lines of pure math with no dependencies, making top-level extension functions (`Double.toMgDl()`, `Double.toMmol()`, `Double.convertTo()`) the idiomatic Kotlin choice.

**Atomic Design for UI components** — components are organised into atoms, molecules, and organisms to keep reusable UI boundaries explicit as the product grows. The flat `components/` alternative was considered and deliberately rejected to preserve a scalable component taxonomy.

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
| Unit tests | JUnit 5 + MockK + Kotest assertions |
| UI tests | Compose Testing (API ≤ 33) |
| Screenshot tests | Paparazzi 2.0.0-alpha05 + JUnit 5 |

---

## Design System

### Theme
Material 3 with Emerald Green (`#10B981`) as primary color. Full light and dark schemes defined.

| Token | Light | Dark |
|---|---|---|
| Primary | `#10B981` | `#10B981` |
| Background | `#F9FAFB` | `#111827` |
| Surface | `#FFFFFF` | `#1F2937` |

### BG Status

| Status | Range (mmol/L) | Range (mg/dL) | Color |
|---|---|---|---|
| Below target | < 2.8 | < ≈50.5 | 🔵 Blue |
| Within target | ≥ 2.8 and < 7.8 | ≥ ≈50.5 and < ≈140.5 | 🟢 Green |
| Above target | ≥ 7.8 and < 10.0 | ≥ ≈140.5 and < ≈180.2 | 🟠 Orange |
| High | ≥ 10.0 | ≥ ≈180.2 | 🔴 Red |

Status is always computed on the value in mmol/L (`toBgStatus()`); the mg/dL boundaries are those thresholds converted with the 18.0182 factor and rounded to one decimal (hence "≈"); the code never compares mg/dL values.

Below target is shown only for stored values under 2.8 mmol/L; the input validator rejects such readings, so it cannot occur through normal entry.

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
- **`LogbookViewModelTest`** — MVI state transitions tested with the real conversion logic because it is pure and dependency-free.

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
- `EntryItemTest` — mmol/L and mg/dL display
- `LogbookContentTest` — full screen integration: empty state, unit selection, input validation, average display, readings list

### Screenshot tests — Paparazzi (`src/test/`)

Paparazzi 2.0.0-alpha05 with JUnit 5 (`setup()`/`teardown()` hooks).

```bash
# Record images
./gradlew app:recordPaparazziDebug

# Verify images (CI)
./gradlew app:verifyPaparazziDebug
```

Golden images are committed under `src/test/snapshots/`. Screenshot tests allow a 0.05% pixel difference to absorb font anti-aliasing differences between macOS and Linux.

> **Note on Paparazzi version:** `1.3.5` (last stable) is incompatible with Gradle 9.x / Kotlin 2.x. `2.0.0-alpha05` is the only version supporting this toolchain. The JUnit 5 API (`setup()`/`teardown()`) is required as the JUnit 4 `@get:Rule` approach does not trigger snapshot recording in this version.

---

## Accessibility

- `FlowRow` used instead of `Row` in all multi-element components (`ReadingItem`, `AverageHeader`, `UnitRadioButton`, `StatusBadge`) — layout reflows correctly at large font sizes
- `contentDescription` on `ReadingItem` card — TalkBack reads the full reading in one announcement: time, value, unit, and status
- `semantics(mergeDescendants = true)` on `AverageHeader` — prevents TalkBack from reading each child element separately
- `role = Role.RadioButton` on `UnitRadioButton` — TalkBack correctly announces it as a radio button
- Decorative icons have `contentDescription = null` — TalkBack skips them

---

## Setup

```bash
git clone <repo>
cd MiniLogbook
./gradlew assembleDebug
```

No API keys or environment variables required.

Minimum SDK: **26** | Target SDK: **36**

---

## Trade-offs

- `material-icons-extended` included for convenience. In production, individual icons would be copied as `ImageVector` constants to avoid the ~17MB dependency size impact.
- Compose UI tests pinned to API ≤ 33 due to an unresolved Espresso/`InputManager` incompatibility on API 34+. Robolectric would be the production solution to remove this constraint.
- Paparazzi on `2.0.0-alpha05` due to Gradle 9.x / Kotlin 2.x toolchain requirements.

---

## Roadmap / production hardening

- **Delete / edit readings** — swipe-to-delete on list items
- **Pagination** — `LazyColumn` with paging for large reading histories
- **Chart** — line chart of readings over time using Vico or MPAndroidChart
- **Dark theme preview** — Paparazzi snapshot with `DeviceConfig` dark mode flag
- **Robolectric** — migrate Compose UI tests to Robolectric to remove the API 34+ Espresso limitation and enable CI without an emulator
- **Release automation** — signed release pipeline, changelog generation, and distribution-track promotion
