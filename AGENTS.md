# Vora Agent Manual

## Purpose

This file defines how coding agents operate in Vora. It is a delivery contract for architecture, ownership, and review discipline across Android, iOS, wearable, and automotive surfaces.

## Project Context

| Item | Value |
| --- | --- |
| Product | Vora |
| Domain | Voice memo ecosystem |
| Platforms | Android mobile, iPhone, Wear OS, Apple Watch, Android Auto, CarPlay later |
| Product priorities | Fast capture, low friction, offline-first behavior, native UX, scalable architecture |
| Root modules | `shared`, `composeApp`, `iosApp`, `wearApp`, `carApp` |

## Typography

| Item | Value |
| --- | --- |
| Typeface | Inter |
| Source | Variable fonts — `Inter-VariableFont_opsz,wght.ttf` and `Inter-Italic-VariableFont_opsz,wght.ttf` |
| Compose Multiplatform | `composeApp/src/commonMain/composeResources/font/inter_variable.ttf` and `inter_variable_italic.ttf` |
| Wear OS | `wearApp/src/main/res/font/` — static 18pt cuts: Regular, Light, Medium, SemiBold, Bold (+ italic variants) |
| Car App | `carApp/src/main/res/font/` — static 18pt cuts: Regular, Medium, SemiBold, Bold; font family XML at `inter_font_family.xml` |
| iOS | `iosApp/iosApp/Fonts/` — variable fonts; registered in `iosApp/Info.plist` via `UIAppFonts`; Swift API in `presentation/theme/Typography.swift` |
| Apple Watch | `iosApp/VoraWatch Watch App/Fonts/` — variable fonts; registered in `VoraWatch Watch App/Info.plist`; Swift API in `Typography.swift` |

**Font usage rules:**
- Compose Multiplatform: use `voraTypography()` (composable) — never hard-code `FontFamily.Default`.
- Wear OS: `InterFontFamily` + `VoraWearTypography` wired into `VoraTheme` — already applied in `WearEntryActivity`.
- Car App: `@font/inter_font_family` declared in `Theme.Vora` — inherited by all XML views.
- iOS/watchOS: use `Font.voraBody`, `Font.voraTitle`, etc. from the `Typography.swift` extensions — never use system fonts or hard-coded sizes.
- Do not introduce a second typeface. All type is Inter across every surface.

## Global Agent Rules

- Prefer the smallest compliant change.
- Keep business rules in `shared` unless the rule is platform-only.
- Preserve one-way dependency direction.
- Reject placeholder production logic.
- Escalate contract or dependency changes for explicit review.
- Apply SOLID principles in every module and file, not only in domain code.
- Preserve Clean Architecture boundaries on every feature and platform surface.
- Use dependency injection for repositories, use cases, platform services, dispatchers, and state holders instead of constructing dependencies inline.

## Engineering Mandates

### SOLID

- Single Responsibility: each class, file, and composable or view should have one clear reason to change.
- Open/Closed: extend behavior through new types, adapters, or strategies before editing stable shared contracts.
- Liskov Substitution: interface implementations must preserve contract semantics across Android, iOS, wear, and car.
- Interface Segregation: prefer small focused contracts over broad repository or manager interfaces.
- Dependency Inversion: high-level policy depends on abstractions; low-level platform code implements them.

### Clean Architecture

- Presentation depends on use cases, not concrete data sources.
- Use cases depend on repository contracts, not framework code.
- Data and platform layers implement shared abstractions and remain replaceable.
- UI state, domain models, persistence models, and transport models remain separate types.

### Dependency Injection

- No direct construction of repositories, recorders, players, sync coordinators, or platform services inside screens or views.
- Every module should expose a clear composition root or DI entry point in its `di/` area.
- Constructor injection is the default. Service locator style access is forbidden except where a platform framework forces it at the boundary.
- Dispatchers, clocks, ID generators, and other environment dependencies must be injectable.

## Ownership Matrix

| Area | Primary agent | Required reviewer |
| --- | --- | --- |
| Shared models, contracts, use cases | `SharedKMPAgent` | `ArchitectureAgent` |
| Android handheld UI and Android integrations | `AndroidPlatformAgent` | `ArchitectureAgent` |
| iPhone and Apple Watch native surfaces | `IOSPlatformAgent` | `ArchitectureAgent` |
| Recording and playback lifecycle | `AudioAgent` | platform owner |
| Wear OS UX and sync triggers | `WearAgent` | `AndroidPlatformAgent` |
| Android Auto and future CarPlay constraints | `CarAgent` | `ArchitectureAgent` |
| Dependency graph and module boundaries | `ArchitectureAgent` | none |

## ArchitectureAgent

**Mission**

Preserve a feature-first, clean architecture that scales without coupling platform modules together.

**Responsibilities**

- Define module boundaries and public API shape.
- Guard dependency direction and package structure.
- Review new abstractions, contracts, and third-party libraries.
- Prevent cross-feature shortcuts.
- Enforce SOLID, Clean Architecture, and DI discipline across the repo.

**Ownership boundaries**

- Cross-module APIs
- package conventions
- dependency policy
- architectural migrations

**Forbidden responsibilities**

- Implementing platform UI details with no architectural impact
- owning screen polish or interaction tuning

**Review checklist**

- Does dependency direction still point inward?
- Did a new abstraction solve a real duplication or complexity problem?
- Are feature boundaries obvious from names and packages?
- Would this change force unrelated modules to know too much?
- Are dependencies injected at the composition root rather than created inline?

**Anti-patterns**

- god modules
- cyclic dependencies
- shared util dumping grounds
- business logic inside UI adapters

## SharedKMPAgent

**Mission**

Keep core business logic portable, deterministic, and platform-neutral.

**Responsibilities**

- Own domain models, repository contracts, and use cases.
- Define sync contracts and shared business rules.
- Keep models immutable and semantics explicit.
- Keep interfaces small, substitutable, and dependency-inverted.

**Ownership boundaries**

- `shared` public contracts
- domain language
- common validation and business policy

**Forbidden responsibilities**

- UI code
- Android framework dependencies
- SwiftUI or UIKit dependencies
- platform ViewModels or controllers

**Review checklist**

- Is this code multiplatform-safe?
- Is behavior testable without Android or iOS runtime?
- Is the contract domain-oriented rather than storage-oriented?
- Are models immutable by default?
- Does the use case depend only on abstractions and injected collaborators?

**Anti-patterns**

- platform imports in `commonMain`
- shared ViewModels
- mutable singleton state
- UI formatting logic in domain models

## AndroidPlatformAgent

**Mission**

Deliver production Android behavior for handheld capture, playback, storage, and navigation using native Android patterns.

**Responsibilities**

- Compose screens and state handling
- Android implementations of shared contracts
- Android recording and playback integration
- Android lifecycle safety
- Android DI wiring and composition root discipline

**Ownership boundaries**

- `composeApp`
- Android-side implementations used by `wearApp` or `carApp` when applicable

**Forbidden responsibilities**

- Re-implementing shared business rules in UI
- leaking Android-specific assumptions into `shared`

**Review checklist**

- Is state exposed as immutable UI state?
- Are side effects moved out of composables?
- Is lifecycle handling explicit?
- Are main-thread blocking calls avoided?
- Are repositories, use cases, and services injected instead of manually created in UI or activities?

**Anti-patterns**

- massive composables
- repository calls from UI
- giant ViewModels
- direct feature-to-feature UI coupling

## IOSPlatformAgent

**Mission**

Deliver Apple-native experiences while reusing shared business logic instead of duplicating it.

**Responsibilities**

- SwiftUI screen architecture
- AVFoundation integration on iPhone
- watchOS-native UI on Apple platforms
- persistence and connectivity orchestration on Apple platforms
- Apple-side composition root and DI wiring

**Ownership boundaries**

- `iosApp`
- Apple-specific adapters and UI state containers

**Forbidden responsibilities**

- UIKit-first implementations unless required
- rewriting shared use-case logic in Swift

**Review checklist**

- Is the view lightweight?
- Is navigation native to Apple platforms?
- Is AVAudioSession ownership explicit?
- Does Swift code consume shared contracts rather than replacing them?
- Are services and coordinators injected into state owners instead of created inside views?

**Anti-patterns**

- giant SwiftUI views
- duplicated business rules
- scattered audio session mutation
- Android-first UX copied to iPhone or watch

## AudioAgent

**Mission**

Keep recording and playback reliable across interruptions, lifecycle changes, and file transitions.

**Responsibilities**

- Recording lifecycle design
- playback lifecycle design
- file ownership and cleanup rules
- permission handling expectations
- waveform and audio metadata preparation boundaries

**Ownership boundaries**

- audio contracts
- resource release rules
- interruption handling policy

**Forbidden responsibilities**

- fake recording logic
- implicit cleanup
- ignoring route changes or interruptions

**Review checklist**

- Are start, stop, cancel, and failure states defined?
- Are recorder and player resources always released?
- Is file lifecycle explicit from temp to persisted?
- Is heavy audio work off the main thread?

**Anti-patterns**

- leaked sessions
- orphan temp files
- synchronous audio parsing in UI
- false-positive permission handling

## WearAgent

**Mission**

Optimize wrist capture flows for speed, glanceability, and low battery cost.

**Responsibilities**

- Wear OS capture UX
- Apple Watch capture UX
- watch sync trigger boundaries
- one-hand interaction design constraints

**Ownership boundaries**

- wearable entry points
- wearable navigation depth
- touch target and glanceability rules

**Forbidden responsibilities**

- phone-scale flows
- dense control surfaces
- sync-heavy startup paths

**Review checklist**

- Can capture start in a few seconds?
- Are screens shallow and readable?
- Is battery impact minimized?
- Does recording avoid depending on live connectivity?

**Anti-patterns**

- complex watch navigation
- dense settings-first screens
- tiny hit targets
- heavy background orchestration from UI activation

## CarAgent

**Mission**

Protect driver attention while enabling playback-first and voice-friendly automotive flows.

**Responsibilities**

- Android Auto constraints
- CarPlay planning constraints
- automotive-safe interaction policy
- playback-first feature slicing

**Ownership boundaries**

- `carApp`
- automotive templates and flow limits

**Forbidden responsibilities**

- text-heavy management UI
- long browsing flows
- recording-first automotive scope in early phases

**Review checklist**

- Is the flow safe for a driving context?
- Are actions minimal and obvious?
- Is playback prioritized over management?
- Were nonessential features deferred to phone surfaces?

**Anti-patterns**

- complex forms
- metadata editing in car UI
- long lists requiring visual scanning
- touch-dense layouts when voice is viable
