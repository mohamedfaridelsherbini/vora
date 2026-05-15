# Vora Architecture

## Overview

Vora is a Kotlin Multiplatform project with shared business logic and native platform presentation layers. The architecture favors fast local capture, offline-first persistence, and native interaction patterns on each surface.

## Architectural Principles

- SOLID applies to every layer, including UI, state holders, use cases, repositories, and platform adapters.
- Clean Architecture is mandatory: policy points inward, implementations point outward.
- Dependency Injection is required across the repo so features remain testable, replaceable, and platform-safe.

## Module Structure

| Module | Responsibility |
| --- | --- |
| `shared` | Domain models, repository contracts, use cases, shared business rules, sync contracts |
| `composeApp` | Android mobile app, Compose UI, Android implementations of shared contracts |
| `iosApp` | iPhone app, SwiftUI UI, Apple implementations of shared contracts |
| `wearApp` | Wear OS app, quick capture UX, wearable-specific Android integrations |
| `carApp` | Android Auto playback-first surface and automotive-safe entry points |

## Responsibility Split

### Shared

- `domain/model`: immutable business models
- `domain/repository`: repository interfaces
- `domain/usecase`: business actions
- `data/*`: shared data shaping contracts and mappers that remain platform-neutral
- `audio/*`: shared audio state models and capability contracts only
- `di/`: shared wiring helpers or contract-level DI utilities only, never platform framework containers

### Android

- Compose screens and navigation
- Android audio recording and playback adapters
- local storage implementations
- permission handling and lifecycle coordination
- Android composition root and DI modules
- route/container/content separation for non-trivial Compose screens
- immutable screen state and event-driven UI updates

### iOS

- SwiftUI screens and navigation
- AVFoundation adapters
- Apple persistence and connectivity adapters
- watchOS-specific Apple-side coordination
- Apple composition root and DI entry points

### Wear OS

- quick capture entry flow
- minimal list or recent memo access if justified
- low-battery orchestration

### Android Auto

- media browsing and playback first
- no recording in early phases
- voice-safe interaction limits

## Dependency Direction

```text
UI -> State Holder -> Use Case -> Repository Contract -> Platform Implementation
```

Dependency creation rule:

```text
Composition Root -> injects State Holder / Use Case / Repository / Platform Service
```

Detailed direction:

```text
composeApp  -> shared
iosApp      -> shared
wearApp     -> shared
carApp      -> shared
shared      -> no platform UI or platform SDKs
```

## Package Structure

### Shared

```text
shared/src/commonMain/kotlin/.../
  domain/
    model/
    repository/
    usecase/
  data/
    datasource/
    local/
    mapper/
    repository/
  audio/
    model/
    player/
    recorder/
  di/
  util/
```

### Android Mobile

```text
composeApp/src/commonMain/kotlin/.../
  presentation/
    notes/
    recorder/
    details/
    navigation/
    designsystem/
    theme/
```

### iOS and Wearables

Use equivalent feature-oriented grouping:

- `presentation`
- `audio`
- `sync`
- `theme`
- `di`

## Dependency Injection Topology

- `shared` defines abstractions and use-case constructors.
- `composeApp`, `wearApp`, `carApp`, and `iosApp` own their composition roots.
- Platform DI layers assemble concrete repositories, audio services, persistence adapters, and state holders.
- UI code receives injected dependencies; it does not create them.

Recommended flow:

```text
Activity/App Entry -> DI Composition Root -> State Holder -> Use Case -> Repository Contract -> Platform Adapter
```

Compose screen shape:

```text
Route -> Screen Container -> Stateless Sections / Components
```

Rules:

- `Route` owns dependency lookup and lifecycle entry wiring only.
- `Screen Container` maps immutable UI state to rendering and emits events upward.
- Stateless components render data and callbacks only.
- Reusable design-system components must not depend on feature state holders or repositories.

## Data Flow

1. UI emits an intent.
2. A state holder translates the intent into a use-case call.
3. The use case applies business rules and calls repository contracts.
4. Platform implementations perform storage, recording, playback, or sync work.
5. The state holder exposes a new immutable UI state snapshot.

## Platform Responsibilities

| Platform | Native responsibilities |
| --- | --- |
| Android mobile | MediaRecorder or equivalent recording stack, Media3 playback, Room or other local persistence, Compose UI |
| iPhone | AVFoundation recording and playback, SwiftUI UI, SwiftData or Core Data if adopted later |
| Wear OS | fast wrist capture, battery-aware behavior, wearable navigation limits |
| Apple Watch | native watch capture UX, WatchConnectivity integration later |
| Android Auto | playback-safe browsing and controls |
| CarPlay later | playback-safe Apple automotive surface after research phase |

## Forbidden Dependencies

- `shared` -> AndroidX, Android SDK, SwiftUI, UIKit, AVFoundation
- `wearApp` -> `composeApp` internals
- `carApp` -> `composeApp` internals
- `iosApp` -> Android modules
- any feature module -> another feature’s internal UI classes

## Architectural Constraints

- Clean architecture boundaries are mandatory.
- SOLID compliance is mandatory.
- Dependency injection is mandatory for all non-trivial dependencies.
- Shared models are immutable.
- UI layers do not own business policy.
- Repository interfaces live in `shared`.
- Platform modules own framework-specific code.
- No cyclic dependencies.
- No direct feature coupling without a stable contract.
- No inline construction of repositories, recorders, players, data sources, or sync coordinators inside UI layers.

## Early Scope Decisions

- Android Auto starts as playback-first, not recording-first.
- CarPlay is intentionally deferred.
- Sync is not part of the first local MVP.
- Shared code exists for business logic, not UI convergence.
