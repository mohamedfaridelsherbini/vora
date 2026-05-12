# Vora AI Engineering Manual

## Purpose

This document is the operating manual for AI-assisted engineering in Vora. It defines architectural authority, module ownership, delivery rules, and review expectations for Codex, Cursor, Claude Code, Gemini, OpenCode, and comparable agents.

Use this file as a binding contract, not as advisory prose. If a generated change conflicts with this document, the change is wrong until reviewed and explicitly approved.

## Project Snapshot

| Item | Definition |
| --- | --- |
| Product | Vora |
| Domain | Multiplatform voice memo ecosystem |
| Platforms | Android mobile, iPhone, Wear OS, Apple Watch, Android Auto, CarPlay later |
| Primary user action | Capture a voice memo with minimal friction |
| Core priorities | Speed, simplicity, offline-first behavior, native UX, scalable architecture |
| Current root modules | `shared`, `composeApp`, `wearApp`, `carApp`, `iosApp` |

## Project Philosophy

### Product Goals

- Voice capture must be available within seconds on phone, watch, and car surfaces.
- Recording must be resilient in low-connectivity and no-connectivity environments.
- Playback and memo access must feel immediate even before sync completes.
- Cross-device behavior must feel coherent without making the UI feel cross-platform or generic.

### UX Philosophy

- Native interaction beats visual consistency when the two conflict.
- Quick capture flows outrank feature depth on wearable and automotive surfaces.
- The default path should minimize taps, waiting, permissions friction, and recovery effort.
- Surfaces used while moving, driving, or multitasking must reduce cognitive load.

### Engineering Philosophy

- Shared logic exists to centralize business rules, not to centralize UI.
- Platform-native code owns platform-native concerns.
- Explicit contracts beat convenience coupling.
- Simple dependency graphs beat clever abstractions.
- Production code must be real, testable, and replaceable. No placeholder logic ships.

## Repo Topology

| Module | Primary role | Owned by |
| --- | --- | --- |
| `shared` | Shared domain, contracts, use cases, sync rules | `SharedKMPAgent` |
| `composeApp` | Android handheld app and Compose UI | `AndroidPlatformAgent` |
| `wearApp` | Wear OS app and quick capture flows | `WearAgent` with `AndroidPlatformAgent` |
| `carApp` | Android Auto and automotive entry points | `CarAgent` with `AndroidPlatformAgent` |
| `iosApp` | iPhone and Apple Watch native app surfaces | `IOSPlatformAgent` with `WearAgent` |

## Global Operating Rules

1. Preserve strict ownership boundaries. Editing outside the owning area requires stating why.
2. Prefer extending existing feature seams over introducing new architectural axes.
3. Do not add new dependencies without documenting why existing platform or standard libraries are insufficient.
4. Treat audio capture, sync, persistence, and lifecycle code as high-risk areas requiring defensive handling and tests.
5. Ship maintainable code only. Stubs, fake production branches, and TODO-driven behavior are prohibited.

## Architecture Rules

### Required Architecture

- Use feature-first organization within each module.
- Keep clean architecture boundaries: UI -> presentation/state -> use cases -> repositories/contracts -> platform or persistence implementations.
- Maintain one-way dependency direction toward stable abstractions.
- Keep shared business rules in `shared`; keep platform orchestration in platform modules.
- Model UI state as immutable snapshots.
- Prefer explicit use cases for business actions with side effects or multi-step rules.

### Dependency Direction

| From | May depend on | Must not depend on |
| --- | --- | --- |
| `shared` | Kotlin stdlib, coroutines, serialization, pure multiplatform libraries | Android SDK, Jetpack, SwiftUI, UIKit, AVFoundation, platform ViewModels |
| `composeApp` | `shared`, AndroidX, Compose, Android media/persistence implementations | `wearApp`, `carApp`, iOS code |
| `wearApp` | `shared`, Android wearable APIs, Android-specific adapters | `composeApp` feature internals, `carApp`, iOS code |
| `carApp` | `shared`, Android Auto/media APIs | `composeApp` UI internals, `wearApp`, iOS code |
| `iosApp` | `shared` framework, SwiftUI, AVFoundation, WatchConnectivity, Apple persistence | Android modules or Android-first assumptions |

### Non-Negotiable Constraints

- No cyclic dependencies.
- No business logic in UI files.
- No direct feature-to-feature coupling without a shared contract or use case boundary.
- No god classes, god ViewModels, or god repositories.
- No persistence schema leakage into domain models.
- No cross-platform duplication of business rules when the rule can live in `shared`.

### Architecture Examples

Good:

```text
RecordMemoButton -> RecordMemoViewModel -> StartRecordingUseCase -> RecordingRepository
```

Bad:

```text
RecordMemoButton -> MediaRecorder + file IO + sync enqueue + analytics
```

## Kotlin Rules

- Use coroutines and structured concurrency for asynchronous work.
- Prefer `suspend` for one-shot operations and `Flow`/`StateFlow` for streams and observable state.
- Avoid launching unmanaged coroutines from UI or repository code.
- Keep models immutable with `data class` plus `val` properties unless mutability is unavoidable and isolated.
- Encode failure with explicit result types, domain errors, or well-scoped exceptions.
- Name use cases with verbs: `StartRecordingUseCase`, `SaveMemoUseCase`, `SyncPendingMemosUseCase`.
- Name repository interfaces by capability, not storage choice: `MemoRepository`, not `RoomMemoRepository` in shared contracts.
- Keep extension functions local to the feature unless they represent a stable shared abstraction.

### Kotlin Anti-Patterns

- `GlobalScope`
- mutable shared singleton state
- hidden dispatcher switching without reason
- suspend functions that block threads
- repository methods returning UI models

## Compose Rules

- Composables default to stateless and receive state plus event callbacks.
- Hoist screen state to a dedicated state holder or ViewModel.
- Keep composables focused and decomposed by responsibility.
- Use previews for reusable components and states where practical.
- Side effects belong in effect APIs or state holders, never inline with rendering logic.
- UI modules may format display data, but they must not contain business policy.

### Compose Anti-Patterns

- 500-line screens
- direct repository calls from composables
- mutable state scattered through child composables
- recording lifecycle logic in a `@Composable`

### Compose Review Checklist

- Is state hoisted?
- Is the composable reusable and testable?
- Are side effects lifecycle-aware?
- Is domain logic kept out of UI?
- Does the component expose clear events instead of internal orchestration?

## SwiftUI Rules

- Keep views lightweight and declarative.
- Use native Apple navigation and interaction patterns unless there is a product reason not to.
- Isolate AVFoundation, WatchConnectivity, and persistence orchestration from view bodies.
- Use observable state objects for screen state and intent handling.
- Share business rules from `shared` rather than recreating them in Swift.
- Prefer SwiftUI-first implementations; use UIKit only when platform APIs force it.

### SwiftUI Anti-Patterns

- giant view bodies with branching business logic
- duplicated validation or sync policy from `shared`
- direct AVAudioSession mutation from multiple unrelated views
- UIKit-first implementations for convenience

## Audio Rules

- Use native audio stacks only: AVFoundation on Apple platforms, Android media APIs including MediaRecorder and Media3 on Android.
- Recording lifecycle must explicitly cover start, pause if supported, resume if supported, stop, cancel, failure, interruption, and cleanup.
- Audio resources must be released deterministically on completion, cancellation, and app backgrounding transitions where required.
- File lifecycle must be explicit: temp, persisted, uploaded, deleted.
- Permissions logic must be truthful and platform-correct. Never simulate microphone access success.
- Waveform generation must not block the main thread.

### Audio Review Checklist

- Are microphone and playback resources always released?
- Are interruptions and route changes handled?
- Is file cleanup deterministic?
- Are long-running operations off the main thread?
- Is failure surfaced to the caller with actionable state?

## Watch Rules

- Optimize for one-hand, glanceable interaction.
- Recording entry must be fast and obvious.
- Keep UI shallow, with minimal screens and minimal typing.
- Prefer larger touch targets and minimal text density.
- Avoid battery-heavy polling, animation, and background work.
- Sync should be opportunistic and resilient, not blocking capture.

### Watch Anti-Patterns

- nested navigation stacks for basic recording
- dense settings screens
- phone-first layouts copied to a watch
- long-running sync work triggered from UI activation

## Car Rules

- Design for driver safety first.
- Make voice-first and playback-focused flows the default.
- Keep actions minimal, large, and obvious.
- Avoid text-heavy layouts, long lists, search-heavy flows, and multi-step forms.
- Do not expose interactions that require prolonged attention.
- Automotive surfaces may play, resume, or capture quick memos, but must not become general-purpose management consoles.

### Car Anti-Patterns

- reading-oriented screens
- detailed metadata editing
- multi-page setup flows
- touch-first interactions where voice is viable

## Ownership Rules

### Module Ownership

| Area | Primary owner | Secondary reviewers | Notes |
| --- | --- | --- | --- |
| Shared contracts, domain models, use cases | `SharedKMPAgent` | `ArchitectureAgent` | Breaking changes require explicit review |
| Android handheld UI and Android implementations | `AndroidPlatformAgent` | `ArchitectureAgent`, `AudioAgent` when audio-related | Compose rules apply |
| iPhone and watchOS native surfaces | `IOSPlatformAgent` | `ArchitectureAgent`, `AudioAgent`, `WearAgent` when relevant | SwiftUI-first |
| Wear OS flows | `WearAgent` | `AndroidPlatformAgent` | Battery and speed constraints dominate |
| Android Auto and future CarPlay behavior | `CarAgent` | `ArchitectureAgent`, platform agent for target OS | Safety constraints dominate |
| Recording/playback pipelines | `AudioAgent` | platform owner + `ArchitectureAgent` | High-risk code path |

### Contract Change Policy

- Only `SharedKMPAgent` may originate new shared contracts or mutate existing ones without first documenting downstream impact.
- Any change to `shared` public APIs requires review from `ArchitectureAgent`.
- Platform agents may implement shared interfaces, but must not silently widen or reinterpret contract semantics.
- Dependency graph changes require architecture review before merge.

### Restrictions Between Layers

- Platform UI may depend on platform state holders and shared use cases.
- Shared code may define contracts for storage, sync, and media coordination, but platform modules implement them.
- Wear and car modules must not import handheld UI internals.
- iOS and Android must never mirror business logic independently if it can live in `shared`.

## Agent Specifications

### 1. ArchitectureAgent

| Topic | Definition |
| --- | --- |
| Mission | Preserve a scalable, modular, feature-first architecture across all platforms |
| Responsibilities | Module boundaries, dependency graph, package structure, architectural consistency, long-term maintainability |
| Ownership boundaries | Cross-module contracts, feature slicing, dependency introduction, public API shape |
| Forbidden responsibilities | Implementing platform-specific UX details when no architecture concern exists |

Architecture rules:

- Enforce strict layer separation.
- Reject direct feature coupling.
- Reject business logic placed in UI or framework adapters.
- Keep modules cohesive and public APIs narrow.

Coding expectations:

- Prefer small interfaces, stable contracts, and explicit ownership.
- Require naming and packaging that reveals feature boundaries.

Anti-patterns:

- god classes
- shared util dumping grounds
- feature modules reaching into each other’s internals
- dependency additions without architectural need

Review checklist:

- Does the change preserve dependency direction?
- Is the feature boundary explicit?
- Is any abstraction premature or overly generic?
- Did a contract change force unrelated modules to change?

### 2. SharedKMPAgent

| Topic | Definition |
| --- | --- |
| Mission | Keep business logic deterministic, portable, and platform-neutral |
| Responsibilities | Domain models, repository contracts, use cases, sync contracts, immutable state |
| Ownership boundaries | `shared` public API, domain rules, sync semantics, error contracts |
| Forbidden responsibilities | UI code, Android APIs, SwiftUI, ViewModels, platform framework orchestration |

Architecture rules:

- Shared code must compile without platform UI dependencies.
- Contracts must model business capability, not UI workflow quirks.
- State and models must default to immutability.

Coding expectations:

- Keep behavior deterministic and testable.
- Prefer pure functions where possible.
- Use explicit interfaces for persistence, recording coordination, and sync triggers.

Anti-patterns:

- platform imports in `commonMain`
- shared ViewModels
- mutable shared caches with implicit lifecycle
- UI formatting logic in domain models

Review checklist:

- Is every dependency truly multiplatform-safe?
- Are models immutable?
- Is the contract focused on domain meaning?
- Could this logic be tested without Android or iOS runtime?

### 3. AndroidPlatformAgent

| Topic | Definition |
| --- | --- |
| Mission | Deliver native Android behavior for handheld, playback, recording, and integration surfaces |
| Responsibilities | Android architecture, Compose screens, recording integration, Media3 playback, Android Auto integration support, Wear OS Android-side plumbing |
| Ownership boundaries | `composeApp` Android code, Android implementations of shared contracts |
| Forbidden responsibilities | Rewriting shared business logic in Android UI, introducing platform-only rules into shared contracts |

Architecture rules:

- Use unidirectional state flow.
- Hoist state and isolate lifecycle-aware orchestration.
- Keep composables reusable and focused.

Coding expectations:

- Respect Android lifecycle boundaries.
- Keep background work off the main thread.
- Use platform APIs directly where appropriate instead of layering unnecessary abstractions.

Anti-patterns:

- massive composables
- repository calls from UI
- mutable state spread across multiple unrelated classes
- blocking IO on the main thread

Review checklist:

- Is UI state immutable at the screen boundary?
- Are lifecycle transitions handled safely?
- Is Media3 or recorder usage scoped and released correctly?
- Did Android code avoid absorbing domain responsibilities?

### 4. IOSPlatformAgent

| Topic | Definition |
| --- | --- |
| Mission | Deliver native Apple platform experiences without duplicating shared business policy |
| Responsibilities | SwiftUI architecture, AVFoundation integration, watchOS integration, Apple-native persistence and connectivity |
| Ownership boundaries | `iosApp`, iPhone UX, watchOS native flows on Apple platforms |
| Forbidden responsibilities | Porting Android UI patterns blindly, recreating shared rules in Swift for convenience |

Architecture rules:

- Views stay lightweight.
- Observable state owns intent handling.
- AVAudioSession handling must be centralized and safe.

Coding expectations:

- Prefer native Apple interaction patterns.
- Keep interoperability with `shared` explicit and thin.
- Use UIKit only when required by platform APIs or constraints.

Anti-patterns:

- giant SwiftUI views
- duplicate use-case logic in Swift
- scattered AVFoundation ownership
- watchOS flows designed like phone screens

Review checklist:

- Is the view lightweight?
- Is Apple-native behavior preserved?
- Are audio session transitions handled safely?
- Does Swift code consume shared contracts instead of replacing them?

### 5. AudioAgent

| Topic | Definition |
| --- | --- |
| Mission | Keep recording and playback stable, safe, and production-realistic across all surfaces |
| Responsibilities | Recording lifecycle, playback lifecycle, file handling, microphone permissions, waveform preparation, cleanup logic |
| Ownership boundaries | Audio-related contracts, implementations, failure handling, resource ownership |
| Forbidden responsibilities | Faking media behavior, ignoring interruption paths, leaving cleanup implicit |

Architecture rules:

- Audio coordination must be explicit.
- File ownership and state transitions must be traceable.
- Platform implementations may differ, but state semantics must remain coherent.

Coding expectations:

- Release resources deterministically.
- Handle interruptions, route changes, and partial failures.
- Keep heavy audio work off the UI thread.

Anti-patterns:

- fake recording logic
- leaked sessions or recorder instances
- orphan temp files
- synchronous waveform parsing on UI thread

Review checklist:

- Can the recording be cancelled safely?
- Is cleanup guaranteed?
- Are interruptions and resume paths defined?
- Are temporary and persisted files clearly separated?

### 6. WearAgent

| Topic | Definition |
| --- | --- |
| Mission | Optimize capture and playback flows for wrist-based speed and low-friction use |
| Responsibilities | Wear OS, Apple Watch, quick capture flows, watch sync behavior |
| Ownership boundaries | Wrist UI, glanceability, low-power interaction patterns |
| Forbidden responsibilities | Phone-scale navigation, dense information layouts, heavy orchestration in watch UI |

Architecture rules:

- Keep screens shallow and intent-driven.
- Prioritize launch speed and minimal taps.
- Use sync as background continuity, not as a gate for recording.

Coding expectations:

- Prefer large targets, short labels, and fast transitions.
- Minimize background work and battery cost.

Anti-patterns:

- complex navigation trees
- dense controls
- large editable forms
- sync-dependent recording start

Review checklist:

- Can the user capture a memo in a few seconds?
- Is battery cost minimized?
- Is UI readable at a glance?
- Is watch sync resilient to disconnects?

### 7. CarAgent

| Topic | Definition |
| --- | --- |
| Mission | Deliver voice-first, distraction-safe automotive flows |
| Responsibilities | Android Auto, future CarPlay planning, audio playback integration, automotive-safe capture flows |
| Ownership boundaries | Automotive templates, voice-driven navigation, playback-first behavior |
| Forbidden responsibilities | Rich management UIs, long text flows, unsafe interaction density |

Architecture rules:

- Prioritize driver-safe constraints over feature parity.
- Keep automotive entry points minimal and focused.
- Defer anything nonessential to phone surfaces.

Coding expectations:

- Favor large actions, predictable states, and short task flows.
- Treat voice interaction as primary where available.

Anti-patterns:

- text-heavy UI
- complex forms
- long browsing flows
- requiring sustained touch interaction

Review checklist:

- Is the flow safe for a driving context?
- Is voice-first behavior available where appropriate?
- Are nonessential features excluded?
- Is playback or quick capture the dominant path?

## Dependency Restrictions

| Rule | Requirement |
| --- | --- |
| Shared dependencies | Must be multiplatform-safe and justified |
| Android dependencies | Must align with Android lifecycle, media, and Compose architecture |
| iOS dependencies | Must align with Apple platform patterns and lifecycle expectations |
| New third-party libraries | Require documented need, maintenance confidence, and review by `ArchitectureAgent` |
| Duplicative libraries | Reject when platform SDK or existing dependency already solves the problem |

## Anti-Patterns

The following are explicitly forbidden in production code:

- god objects
- cross-feature shortcuts that bypass contracts
- giant ViewModels or giant state holders
- mutable shared state without explicit synchronization and ownership
- business logic in composables or SwiftUI views
- duplicated business rules across Android and iOS
- fake implementations presented as real features
- placeholder production code, sample code, or mock data paths left active
- hidden side effects in mappers or model getters
- broad `util` packages used as dumping grounds

## Development Workflow

### Adding a Feature

1. Define the user-facing capability and the target surfaces.
2. Decide what belongs in `shared` versus what is platform-native.
3. Add or extend shared contracts only when multiple platforms truly need the same business semantics.
4. Implement platform adapters and UI using native patterns.
5. Add tests at the highest-value layer: shared business rules first, platform lifecycle code where risk is high.
6. Validate offline behavior, interruption behavior, and recovery behavior.

### Review Expectations

- Every change must preserve ownership boundaries.
- Shared contract changes require architecture review.
- Audio changes require audio-specific review.
- Wear and car changes are reviewed against speed and safety constraints, not only code style.
- Reviewers should reject speculative abstraction and weak placeholder logic.

### Dependency Review Process

1. State the problem the dependency solves.
2. Explain why platform SDKs or current dependencies are insufficient.
3. Document module scope and transitive impact.
4. Confirm license, maintenance status, and multiplatform compatibility where relevant.
5. Obtain `ArchitectureAgent` approval before merge.

### Architectural Validation

Use this pre-merge gate:

- Are boundaries still clean?
- Is dependency direction preserved?
- Did shared code stay platform-neutral?
- Did platform code stay native and lifecycle-safe?
- Did recording, playback, file cleanup, and offline paths get explicit treatment?

## Change Acceptance Criteria

A change is not production-ready unless all of the following are true:

- The owning agent’s rules are satisfied.
- No forbidden responsibility was absorbed.
- The implementation uses real logic, not placeholders.
- The code is testable and readable by the next engineer.
- The architecture is more explicit or at least no less clear than before.

## Final Instruction to AI Agents

When uncertain, choose the narrower change, the cleaner boundary, and the more native platform behavior. If a request would violate this document, stop and propose the smallest compliant alternative instead of generating compromised code.
