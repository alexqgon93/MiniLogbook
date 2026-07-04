# Contributing

## Commit conventions

Use Conventional Commits:

- `feat:` for user-facing features
- `fix:` for bug fixes
- `refactor:` for behavior-preserving code changes
- `test:` for test-only changes
- `docs:` for documentation-only changes
- `chore:` for build, dependency, and repository maintenance

## Quality gates

Before opening a pull request, run:

```bash
./gradlew app:ktlintCheck app:testDebugUnitTest app:verifyPaparazziDebug
```

## Architecture rules

- Keep domain models framework-agnostic.
- Put Android resource mapping in presentation/UI code.
- Keep Room entities and database concerns in the data layer.
- Prefer explicit migrations over destructive migrations.
- Add tests for validation boundaries, persistence changes, and UI state transitions.

## Architecture documentation

Architecture decisions live under `docs/adr/`. Add or update an ADR when changing module boundaries, persistence strategy, migration policy, release process, observability, or privacy/security behavior.
