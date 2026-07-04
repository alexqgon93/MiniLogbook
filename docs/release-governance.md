# Release Governance

## Versioning

Use monotonically increasing `versionCode` and human-readable `versionName`. Any schema change must mention the database version and migration behavior in release notes.

## Release verification

Release candidates should pass:

```bash
./gradlew app:ktlintCheck app:testDebugUnitTest app:verifyPaparazziDebug app:assembleRelease
```

## Signing

Production signing keys must not be committed. Configure signing through local environment variables, CI secrets, or the distribution platform.

## Changelog

Each release should document:

- user-facing changes
- database/schema changes
- migration risks
- known limitations
- rollback considerations

## Rollback

If a release changes database schema, rollback must account for downgrade behavior. Do not assume users can safely install an older APK after schema migration.
