# 0005: Introduce :core:design-system Module

- Status: Accepted — 2026-05-24
- Date: 2026-05-24

## Context

As feature modules multiplied, each screen was independently reaching for
`MaterialTheme.colorScheme.*` and hardcoding spacing values (e.g., `16.dp`).
This created two concrete problems:

1. **Inconsistency**: Colors and spacing could diverge between features with no
   compile-time guard.
2. **Coupling to M3 internals**: Feature modules directly referenced
   `MaterialTheme.colorScheme`, making it hard to swap or extend the token layer.

We also had a legacy `app/theme/` package (`Color.kt`, `Theme.kt`, `Type.kt`) that
lived inside `:app`, making the theme inaccessible to `:feature:*:ui` modules
without an unacceptable dependency on `:app`.

## Decision

We introduce a `:core:design-system` module that owns the entire token and component layer.

**Theme access** is exposed only through `PlaygroundTheme`:

```kotlin
PlaygroundTheme.appColors.text.primary       // semantic color tokens
PlaygroundTheme.appColors.accent.primary
PlaygroundTheme.space.medium                 // spacing tokens (16dp)
PlaygroundTheme.typography.titleLarge        // re-export of M3 typography
```

**Shared components** bundled in the module:

| Component | Usage |
|---|---|
| `PrimaryButton` / `SecondaryButton` / `TertiaryButton` | Action hierarchy |
| `PlaygroundCard` | Tappable and static card container |
| `PlaygroundTextField` | Text input |
| `PlaygroundTopAppBar` | Screen-level top bar |

The legacy `app/theme/` package is deleted. All feature modules consume
`:core:design-system` via the `playground.android.feature` convention plugin,
which injects the dependency automatically.

Direct use of `MaterialTheme`, `PlaygroundTheme.colorScheme`, or hardcoded
`dp` values in feature modules is prohibited (enforced by the design-system rule).

## Consequences

**Positive:**
- Single source of truth for colors, spacing, and components across all features.
- Semantic token names (`accent.primary`, `status.error`) make intent clear and
  allow future theme swapping without touching feature code.
- Shared components reduce duplicated Composable code across features.
- The `playground.android.feature` plugin auto-injects the dependency, so
  new features get the design system for free.

**Negative:**
- Any change to `:core:design-system` triggers recompilation of all feature modules.
- Developers must learn the token API before writing new UI, adding initial friction.
- The module boundary adds one more layer of indirection compared to direct M3 usage.
