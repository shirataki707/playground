# 0001: Adopt Jetpack Compose for UI Layer

- Status: Accepted — 2026-05-24
- Date: 2026-05-24

## Context

This project is a technology playground for experimenting with modern Android development.
The Android ecosystem offers two UI frameworks: the traditional View-based XML layout system
and the declarative Jetpack Compose toolkit introduced in 2021.

The View system requires maintaining separate layout XML files, manual view binding, and
imperative state management. Compose provides a single-language, declarative approach
that aligns better with modern reactive programming patterns and reduces boilerplate.

Since this project is intended to explore and learn cutting-edge Android technologies,
choosing the most modern and Google-endorsed UI toolkit is a natural fit.

## Decision

We adopt Jetpack Compose as the sole UI framework for all screens in this project.
No XML layouts will be created. All UI components are written as `@Composable` functions
in Kotlin.

We use the Compose BOM (`androidx.compose:compose-bom`) to manage Compose library versions
consistently, and Material3 (`androidx.compose.material3`) as the design component library.

## Consequences

**Positive:**
- Significantly less boilerplate compared to View-based UI.
- UI state is expressed declaratively, making it easier to reason about.
- Preview support in Android Studio enables rapid visual iteration.
- Strong alignment with Google's recommended architecture and future direction.
- Kotlin-only codebase: no context switching between XML and Kotlin.

**Negative:**
- Compose has a learning curve, especially for developers experienced only with Views.
- Some older third-party libraries do not yet support Compose interoperability.
- Build times can be slightly longer due to the Compose compiler plugin.
