# Health Data Policy

MiniLogbook stores blood glucose readings, which should be treated as sensitive health-related data.

## Local storage

- Readings are stored locally in Room.
- Destructive migrations are not allowed.
- Future schema changes must preserve existing readings.

## Backup

The current manifest allows Android backup. Before production release, confirm whether health readings should be included in device/cloud backup for the target market and privacy requirements.

If backup is not appropriate, disable it or exclude the database through backup rules.

## Logging

- Do not log blood glucose values.
- Do not log full database rows.
- Do not include readings in crash breadcrumbs, analytics events, or diagnostic metadata.

## User rights

Before production release, define:

- delete-all-readings behavior
- export format
- retention policy
- privacy disclosure wording

## Encryption

Room is not encrypted by default. If product requirements classify readings as sensitive at rest, evaluate SQLCipher or platform-backed encrypted storage for the database or relevant fields.
