# 0003: Centralize Build Configuration with build-logic Convention Plugins

- Status: Accepted — 2026-05-24
- Date: 2026-05-24

## Context

With multiple Gradle modules, each module needs to declare Android configuration
(`compileSdk`, `minSdk`, `targetSdk`, `jvmToolchain`) and common dependencies
(Compose, lint tooling, etc.). Duplicating these settings across every
`build.gradle.kts` file creates several problems:

- SDK version bumps require editing every module individually.
- It is easy to accidentally use different settings across modules.
- Adding a new linting tool or dependency requires touching all modules.

Gradle's `buildSrc` or `build-logic` composite-build pattern solves this by
allowing shared build logic to be written as reusable convention plugins.

## Decision

We use a `build-logic` composite build that exposes convention plugins.
Each module applies exactly one plugin from the table below:

| Use case | Plugin ID |
|---|---|
| App module (Compose) | `playground.android.application.compose` |
| Library (no Compose) | `playground.android.library` |
| Library (with Compose) | `playground.android.library.compose` |
| Feature UI module | `playground.android.feature` |
| Feature contract module | `playground.android.feature.contract` |

All plugins enforce `compileSdk = 36`, `minSdk = 24`, `targetSdk = 36`,
`jvmToolchain(17)`, ktlint, and detekt. Compose dependencies are managed
by the `library.compose` plugin rather than repeated in each module.

The `playground.android.feature` plugin automatically injects all
`:feature:*:contract` modules as dependencies, so new contract modules
are picked up without manual wiring.

## Consequences

**Positive:**
- SDK/JVM/lint configuration is defined once and consistent across all modules.
- Adding a new module is a one-liner (`id("playground.android.feature")`).
- A single change to a plugin propagates to all modules that use it.

**Negative:**
- Developers must understand the `build-logic` composite build to modify plugin behavior.
- AGP 9.0 has restrictions (e.g., the Kotlin Android plugin must not be applied
  explicitly since AGP 9.0 bundles Kotlin support), so plugin code requires care.
- Convention plugins add a build-configuration compilation step on clean builds.
