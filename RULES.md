# Vora Engineering Rules

## Enforcement

These rules are mandatory. A change that violates them should be rejected rather than explained away.

## Communication Efficiency Rules

- Prefer the smallest sufficient response.
- Do not restate the user prompt.
- Do not explain obvious steps unless asked.
- Do not output long plans when a short action list is enough.
- Do not summarize unchanged files or unchanged code.
- When reporting work, describe only what changed, why it changed, and any blocker or risk.
- Use bullets only when they improve clarity.
- Avoid repeated warnings, repeated summaries, and generic best-practice filler.
- For simple tasks, answer in one to three short paragraphs or a short list.
- For implementation requests, do the work first and explain briefly after.
- Ask at most one clarifying question unless more are strictly necessary.
- Prefer exact file references and concrete changes over narrative explanation.

Do not:

- repeat the same point in multiple sections
- produce a plan unless the task is ambiguous, risky, or explicitly asks for one
- expand a short answer into a framework or taxonomy the user did not ask for
- include low-signal recap when the result is already clear

## SOLID Rules

- Every type must have one clear responsibility.
- Prefer composition over inheritance unless inheritance is required by a platform API.
- Keep interfaces narrow and capability-focused.
- High-level policy must depend on abstractions, not concrete implementations.
- Concrete implementations must remain safely substitutable for their interfaces.

Do not:

- create god classes, god repositories, or god ViewModels
- merge unrelated concerns into one screen state holder or service
- expose broad interfaces that force callers to depend on methods they do not use
- make domain or presentation layers depend directly on framework implementations

## Clean Architecture Rules

- Presentation depends on use cases and UI models only.
- Use cases depend on repository contracts and policy abstractions only.
- Data and platform layers implement contracts but do not own business policy.
- Keep domain models, persistence models, DTOs, and UI state separate.
- Every cross-layer boundary must be explicit.

Do not:

- place business rules in activities, composables, or SwiftUI views
- let repositories return framework types to domain or presentation layers
- let persistence schema shape domain APIs
- bypass use cases for state-changing behavior

## Dependency Injection Rules

- Use dependency injection across all modules.
- Constructor injection is the default for use cases, repositories, coordinators, state holders, and adapters.
- Every platform module must have a composition root in its `di/` area.
- Inject dispatchers, clocks, ID generators, and platform wrappers when they affect behavior or testing.
- Keep DI setup explicit and easy to trace.

Do not:

- instantiate repositories, recorders, players, or sync coordinators inside screens or views
- hide dependencies behind global mutable singletons
- use service locator patterns except at unavoidable platform boundaries
- couple DI wiring to unrelated UI rendering code

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
