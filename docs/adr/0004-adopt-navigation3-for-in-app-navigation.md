# 0004: Adopt Navigation3 for In-App Navigation

- Status: Accepted — 2026-05-24
- Date: 2026-05-24

## Context

Jetpack Navigation Component (Navigation 2.x) is the established solution for
Compose navigation. However, it has known design limitations:

- The `NavController` API is not type-safe by default; destinations are strings or IDs.
- Passing complex objects between destinations requires extra effort (Parcelable or JSON).
- The single-backstack model is difficult to extend for multi-pane or adaptive layouts.

AndroidX Navigation3 (`androidx.navigation3`, version 1.0.x as of this project's start)
is a ground-up redesign that addresses these issues:

- Destinations are Kotlin objects annotated with `@Serializable` (`NavKey`), providing
  compile-time type safety.
- The back stack is a plain `List<NavKey>`, making it transparent and easy to manipulate.
- `EntryProvider` / `entryProvider` DSL maps NavKeys to composables without a
  centralized navigation graph.

Since this project is a playground for modern Android techniques, adopting Navigation3
early aligns with the project's experimental intent.

## Decision

We adopt `androidx.navigation3` as the navigation framework.

- Each feature defines a `NavKey` (a `@Serializable data object`) in its `:contract` module.
- Each feature's `:ui` module provides an `EntryProviderScope` extension function
  (`<name>Entry`) that registers its screen.
- `:app` composes all entries into a single `entryProvider` block in `Navigation.kt`.
- A custom `Navigator` and `NavigationState` (in `:core:navigation`) wrap the
  `List<NavKey>` back stack for push/pop operations.

Dependencies:
- `androidx.navigation3:navigation3-runtime`
- `androidx.navigation3:navigation3-ui`
- `androidx.lifecycle:lifecycle-viewmodel-navigation3`

## Consequences

**Positive:**
- Type-safe navigation: passing the wrong key type is a compile error.
- No centralized navigation graph; each feature registers itself independently.
- The back stack is a plain list, making deep link and state restoration straightforward.
- Strong alignment with the multi-module contract/UI split (ADR-0002).

**Negative:**
- Navigation3 is not yet stable (1.0.x pre-release as of adoption). API may change.
- Community resources and third-party integrations are still limited compared to Nav2.
- Team members familiar only with Navigation 2.x need to learn the new model.
