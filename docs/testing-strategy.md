# Testing Strategy

## Current coverage

- Unit tests cover conversion, formatting, repository mapping, use cases, and ViewModel state.
- Compose UI tests cover reusable UI components and full-screen behavior.
- Paparazzi snapshot tests cover visual regression for core UI components.
- Migration tests validate Room schema compatibility and data preservation.

## Shared testing module

The project includes `:core:testing` for:

- test fixtures such as sample readings
- fake repositories
- coroutine dispatcher rules
- Room database helpers
- Compose test utilities
- shared accessibility assertions

## Test ownership rules

- Domain logic should be tested with local JVM tests.
- Room schema and migration behavior should be tested with Android instrumented tests.
- UI state transitions should be tested at ViewModel level.
- Reusable UI components should have Compose tests and/or screenshot tests.
- Release-impacting changes should keep CI green for lint, unit tests, screenshot verification, and release assembly.
