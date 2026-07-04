# ADR 0002: Store glucose readings in mmol/L

## Status

Accepted

## Context

The app supports both mmol/L and mg/dL display units. Persisting mixed units would complicate averages, status calculation, and unit switching.

## Decision

Persist all glucose values in mmol/L. Convert to mg/dL only at presentation boundaries.

## Consequences

- Average calculation uses one canonical unit.
- Status thresholds are evaluated consistently.
- Unit switching is immediate and does not require database changes.
- Conversion precision and formatting must be tested because user-visible values are derived.
