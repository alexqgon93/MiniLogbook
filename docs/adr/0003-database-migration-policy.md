# ADR 0003: Database migration policy

## Status

Accepted

## Context

Blood glucose readings are user health data. Destructive migrations are not acceptable for production because they can delete user history.

## Decision

Room must use explicit migrations. `fallbackToDestructiveMigration` is not allowed in production code.

For version 1 there are no migrations yet, so the migration registry is intentionally empty. Future schema changes must add:

- a Room `Migration`
- an exported schema
- a migration test proving existing readings are preserved
- release notes describing the data change

## Consequences

- Schema changes require deliberate review.
- User health data is protected from accidental deletion.
- Migration testing should be added before the next schema version.

## Verification

Version 1 schema validation is covered by `AppDatabaseMigrationTest`. Future migrations must extend this test suite with a pre-migration database setup and post-migration assertions for preserved readings.
