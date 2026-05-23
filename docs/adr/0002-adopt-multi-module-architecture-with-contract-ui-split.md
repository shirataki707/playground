# 0002: Adopt Multi-Module Architecture with Contract/UI Split

- Status: Accepted — 2026-05-24
- Date: 2026-05-24

## Context

As the project grows with multiple features, placing all code in a single `:app` module
causes the following problems:

- Long incremental build times because unrelated code is recompiled together.
- No enforced separation between feature boundaries, making accidental coupling easy.
- Difficult to reason about what a feature depends on vs. what it exposes to others.

A common solution is to split code into multiple Gradle modules. However, a flat split
(one module per feature) still allows features to directly depend on each other's
implementation details.

The `:feature:xr` module was the first feature added, providing a natural opportunity
to establish a multi-module convention for the whole project.

## Decision

We adopt a multi-module architecture with the following module hierarchy:

```
:app                        # Single entry point; wires all features together
:core:navigation            # NavigationState and Navigator shared across features
:core:design-system         # Shared theme, tokens, and UI components
:feature:<name>:contract    # Public API of a feature: NavKey (no Compose dependency)
:feature:<name>:ui          # Screen composables and EntryProvider (Compose dependency)
```

Key rules:
- `:feature:*:contract` modules depend only on Navigation3 runtime and Kotlin Serialization.
  They declare no Compose dependency, keeping them lightweight.
- `:feature:*:ui` modules declare the screen implementation and depend on their own
  `:contract` module plus `:core:*` modules.
- Features must not depend on other features directly. Cross-feature navigation is
  done through `:core:navigation` and NavKeys defined in `:contract` modules.
- `settings.gradle.kts` uses automatic scanning of the `feature/` directory,
  so new modules are discovered without manual registration.

## Consequences

**Positive:**
- Incremental builds are faster: only changed modules are recompiled.
- Clear separation between a feature's public contract and its implementation.
- Features cannot accidentally couple to each other's internals.
- The automatic module scanning reduces the maintenance burden of `settings.gradle.kts`.

**Negative:**
- More Gradle modules means more `build.gradle.kts` files to maintain.
- Initial setup per feature requires creating two modules (`contract` and `ui`).
- Developers unfamiliar with multi-module projects need onboarding.
