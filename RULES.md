# Vora Engineering Rules

## Enforcement

These rules are mandatory. A change that violates them should be rejected rather than explained away.

## Kotlin Rules

- Use `val` by default.
- Use `data class` for immutable state snapshots and domain models.
- Use coroutines for async work.
- Use `suspend` for one-shot operations and `Flow` or `StateFlow` for streams.
- Keep coroutine scope ownership explicit.
- Inject dispatchers or isolate dispatcher choice behind infrastructure boundaries when needed.

Do not:

- block the main thread
- use `GlobalScope`
- share mutable singleton state
- hide side effects in getters or mappers
- use inheritance where composition is simpler

## KMP Rules

- `shared` contains business rules, repository contracts, use cases, and platform-neutral models.
- `shared` must not depend on Android SDK, Jetpack, SwiftUI, UIKit, or AVFoundation.
- Platform modules implement shared contracts; they do not redefine them casually.
- Keep `commonMain` deterministic and unit-testable.
- Prefer `expect` and `actual` only when an interface plus platform implementation is insufficient.

Do not:

- put UI code in `shared`
- put ViewModels in `shared`
- leak storage schema into domain contracts
- duplicate business rules on Android and iOS

## Compose Rules

- Composables are stateless by default.
- Hoist state to a screen-level state holder.
- Keep side effects in effect handlers or ViewModels, not render code.
- Build reusable components for memo cards, controls, and empty states.
- Keep navigation orchestration outside leaf composables.

Do not:

- call repositories from composables
- keep business logic in composables
- build massive screens instead of smaller components
- spread mutable state across sibling composables

## SwiftUI Rules

- Views must stay lightweight.
- Use `ObservableObject` or the project’s chosen observable state pattern for screen state.
- Follow native Apple navigation and interaction conventions.
- Isolate AVFoundation and WatchConnectivity orchestration from view bodies.

Do not:

- default to UIKit when SwiftUI is sufficient
- duplicate shared use-case logic in Swift
- mutate AVAudioSession from multiple unrelated views

## Audio Rules

- Use native platform audio APIs only.
- Model recording lifecycle explicitly: idle, preparing, recording, stopping, completed, failed, cancelled.
- Model playback lifecycle explicitly.
- Release recorder, player, and audio session resources deterministically.
- Keep file lifecycle explicit: temp, saved, deleted.
- Handle interruptions, route changes, and permission denial as first-class cases.

Do not:

- fake recording state
- leave cleanup to process death
- block UI while preparing waveforms or metadata
- assume permissions are already granted

## Watch Rules

- Optimize for fast capture and one-hand use.
- Use large targets and short labels.
- Keep navigation shallow.
- Minimize background work and battery impact.

Do not:

- mirror phone layouts on a watch
- require typing for primary flows
- gate capture on sync availability

## Car Rules

- Prioritize safety over parity.
- Keep flows voice-friendly and playback-first.
- Minimize required attention and touch steps.
- Defer nonessential management actions to handheld apps.

Do not:

- present text-heavy UI
- add long browsing flows
- add complex forms
- treat automotive as a full management surface

## Testing Rules

- Test shared business rules in `shared` first.
- Test state holders where branching logic exists.
- Test audio lifecycle transitions where behavior is risky.
- Prefer deterministic tests over framework-heavy tests.
- Add regression tests for every bug fixed in domain or state logic.

Do not:

- rely only on manual testing for recording, playback, and deletion paths
- merge contract changes without test updates
- use brittle UI tests as a substitute for domain coverage

## Naming Rules

- Name use cases with verbs: `StartRecordingUseCase`.
- Name repositories by capability: `VoiceMemoRepository`.
- Name UI state types by screen or feature: `RecorderUiState`.
- Name composables by rendered role: `MemoCard`, `PlaybackControl`.
- Name interfaces for behavior, not implementation detail.

Do not:

- use vague names like `Manager`, `Helper`, or `Util` for core logic
- name shared contracts after storage engines
- use inconsistent feature names across modules
