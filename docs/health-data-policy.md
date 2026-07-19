# Health Data Policy

MiniLogbook stores blood glucose readings, which should be treated as sensitive health-related data.

## Local storage

- Readings are stored locally in Room.
- Destructive migrations are not allowed.
- Future schema changes must preserve existing readings.

## Backup

The manifest allows Android backup for non-sensitive app state, but Room readings are excluded from cloud backup and device-to-device transfer through `backup_rules.xml` and `data_extraction_rules.xml`.

If future product requirements allow health-reading backup, that decision must be captured with explicit privacy wording, migration/restore behavior, and security review before removing the database exclusions.

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
