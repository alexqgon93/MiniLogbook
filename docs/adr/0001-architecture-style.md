# ADR 0001: Clean Architecture with MVI presentation

## Status

Accepted

## Context

MiniLogbook is a single-feature Android app that stores and displays blood glucose readings. The feature is small, but it benefits from clear boundaries because it includes persistence, validation, unit conversion, presentation state, and UI testing.

## Decision

Use Clean Architecture package boundaries with an MVI-style presentation layer:

- `domain` owns framework-independent models, repository contracts, and use cases.
- `data` owns Room entities, DAO access, mappings, and repository implementations.
- `presentation` owns Compose UI, UI state, UI events, formatting, and Android resource mapping.
- `di` wires app-level dependencies through Hilt.

The ViewModel exposes a single immutable `StateFlow<LogbookUiState>`, and the UI sends sealed `LogbookUiEvent` instances.

## Consequences

- Domain logic remains easy to test.
- Presentation state transitions are explicit.
- Android resource mapping stays outside the domain layer.
- The current repository still uses package boundaries rather than Gradle module boundaries; a future architecture phase should enforce this with modules.
