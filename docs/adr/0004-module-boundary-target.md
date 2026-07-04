# ADR 0004: Target module boundaries

## Status

Proposed

## Context

The app currently has a single Gradle module, `:app`, with clean package boundaries. This is sufficient for a small technical assessment but does not enforce architectural ownership or dependency direction.

## Decision

If the product grows, split the codebase into these modules:

- `:core:domain` — pure Kotlin models, validation, conversion, repository contracts, use cases
- `:core:data` — repository implementations and data mappers
- `:core:database` — Room database, entities, DAO, migrations
- `:core:ui` — theme, design tokens, reusable components
- `:core:testing` — fakes, fixtures, coroutine rules, Compose test helpers
- `:feature:logbook` — logbook presentation state, ViewModel, and feature UI
- `:app` — application wiring, navigation shell, Hilt setup

## Consequences

- Build boundaries will enforce Clean Architecture rules.
- Shared UI and testing infrastructure become reusable.
- The migration is larger than the current feature scope and should be done as a dedicated refactor.

## Migration sequence

1. Create empty Gradle modules and wire dependency direction.
2. Move domain models/use cases into `:core:domain`.
3. Move Room entities/DAO/database/migrations into `:core:database`.
4. Move repository implementations/mappers into `:core:data`.
5. Move theme/design tokens/reusable components into `:core:ui`.
6. Move logbook ViewModel/state/screen into `:feature:logbook`.
7. Keep `:app` as composition root only.

## Dependency direction

```text
:app
  -> :feature:logbook
  -> :core:ui
  -> :core:data
  -> :core:database
  -> :core:domain
```

Feature modules may depend on domain and UI. Data modules may depend on domain. Domain must not depend on Android UI, Room, Hilt, or app resources.
