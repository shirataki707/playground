# 0006: Slim Down :core:design-system to :core:theme

- Status: Accepted — 2026-05-28
- Date: 2026-05-28

## Context

[ADR 0005](0005-introduce-core-design-system-module.md) introduced `:core:design-system`
with shared component wrappers (`PrimaryButton`, `PlaygroundCard`, `PlaygroundTextField`,
`PlaygroundTopAppBar`) alongside theme tokens.

In practice, the component wrappers added no meaningful value over direct Material3 usage:
they were thin pass-throughs with no custom logic, token binding, or enforcement.
Maintaining them created unnecessary indirection and recompilation pressure on all
feature modules whenever a component signature changed.

The only genuinely useful abstraction was `PlaygroundTheme` (dynamic color, dark mode)
and `PlaygroundSpacing` (centralized spacing tokens).

## Decision

We replace `:core:design-system` with a slimmer `:core:theme` module:

- **Kept**: `PlaygroundTheme`, `PlaygroundColors`, `PlaygroundSpacing` — the token layer
- **Removed**: All component wrappers (`PrimaryButton`, `PlaygroundCard`,
  `PlaygroundTextField`, `PlaygroundTopAppBar`)
- **Package rename**: `jp.shirataki707.playground.core.designsystem.theme`
  → `jp.shirataki707.playground.core.theme`

Feature modules use Material3 components directly. Where a `PlaygroundTopAppBar` wrapper
was required (e.g., `XrScreen`), it is replaced with `TopAppBar` inline.

The `playground.android.feature` convention plugin now injects `:core:theme` instead of
`:core:design-system`.

## Consequences

**Positive:**
- Reduced module surface; component API changes no longer trigger cascading recompilation.
- Feature code is more readable — Material3 components appear directly, not behind opaque wrappers.
- Less maintenance burden: no wrapper layer to keep in sync with M3 API changes.

**Negative:**
- Features must import and compose Material3 components directly; no shared shorthand for
  common patterns like a back-navigation top bar.
- If a cross-cutting UI change is needed (e.g., swap icon style), it must be made in each
  feature rather than in one wrapper.
