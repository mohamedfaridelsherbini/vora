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

## Global Agent Rules

- Prefer the smallest compliant change.
- Keep business rules in `shared` unless the rule is platform-only.
- Preserve one-way dependency direction.
- Reject placeholder production logic.
- Escalate contract or dependency changes for explicit review.

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
