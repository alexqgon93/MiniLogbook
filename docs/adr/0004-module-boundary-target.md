# ADR 0004: Target module boundaries

## Status

Accepted

## Context

The app previously used a single Gradle module, `:app`, with clean package boundaries. That was sufficient for the initial product scope but did not enforce architectural ownership or dependency direction.

## Decision

Split the codebase into these modules:

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
- The app module remains the composition root.
- Feature and core modules now enforce clearer dependency direction.
- App tests still live in `:app` for now; future work can redistribute tests closer to their owning modules.

## Migration sequence

1. Created Gradle modules and wired dependency direction.
2. Moved domain models/use cases into `:core:domain`.
3. Moved Room entities/DAO/database/migrations into `:core:database`.
4. Moved repository implementations/mappers into `:core:data`.
5. Moved theme/design tokens/reusable components into `:core:ui`.
6. Moved logbook ViewModel/state/screen into `:feature:logbook`.
7. Kept `:app` as composition root only.

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
