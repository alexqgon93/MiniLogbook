# Observability Strategy

## Goals

Observability should help diagnose production issues without exposing health data.

## Logging

Allowed:

- lifecycle events without readings
- database migration version
- high-level operation success/failure
- non-sensitive exception type and source

Not allowed:

- glucose values
- timestamps tied to individual readings
- full database rows
- user-entered input values

## Crash reporting

If crash reporting is added, configure it to avoid sensitive breadcrumbs and custom keys. Store only coarse, non-health metadata such as app version, database version, selected unit, and screen name.

## Metrics

Useful privacy-safe metrics:

- app start success/failure
- database migration success/failure
- save operation failure count
- load operation failure count
- screen render/performance metrics

## Error handling

Retryable errors should be visible to users with a recovery action. Non-retryable errors should be explicit and test-covered.
