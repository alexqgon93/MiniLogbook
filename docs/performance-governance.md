# Performance Governance

## Current baseline

MiniLogbook is a small single-screen app. The current release build is R8-optimized and CI verifies that release builds assemble successfully.

## Performance risks

- Reading history is exposed as a bounded query window instead of loading the full table.
- A much longer product history may eventually need Paging 3 for interactive archive navigation.
- `material-icons-extended` is convenient but increases dependency and binary size.

## Targets

For production growth, define these budgets:

- cold start target
- first contentful render target
- maximum supported reading count before paging is required
- APK size budget
- screenshot and UI test runtime budget

## Required tooling when scope grows

- Paging 3 if the product adds interactive archive navigation beyond the current bounded history window
- baseline profile or macrobenchmark module for startup and scroll performance
- APK size monitoring in CI
- large-list Compose performance checks

## Regression policy

Any change that increases data volume, adds heavy dependencies, or changes list rendering should document the performance impact and include either a benchmark, a targeted large-data test, or an explicit risk acceptance.
