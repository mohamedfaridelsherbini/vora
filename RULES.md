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

## Unified UI Architecture Rules

- Use one mental model across Compose, SwiftUI, and KMP presentation boundaries.
- Organize by feature, not by widget type or framework convenience.
- UI rendering is stateless by default.
- UI receives immutable `UiState` plus explicit `Action`/`Event` callbacks.
- Navigation orchestration belongs to route/view entry layers, not reusable components.
- Shared/domain/data concerns must not leak into UI files.

Every feature should follow this layout:

```text
feature/
  presentation/
    screen/
    components/
    preview/
    state/
    action/
    viewmodel/
    navigation/
  domain/
  data/
```

## Compose Rules

- Compose structure must follow: `Route -> Screen -> Content -> Components`.
- `Route` collects `Flow`/`StateFlow`, wires `ViewModel`, and coordinates navigation callbacks.
- `Screen` orchestrates layout and maps UI state only.
- `Content` is pure/stateless rendering from immutable `UiState` and callbacks.
- `Components` hold reusable or complex UI blocks.
- Hoist mutable state out of leaf composables.
- Keep side effects in `ViewModel` or controlled effect handlers, never in rendering branches.
- Use feature-based naming and files, for example:
  - `NotesListRoute.kt`
  - `NotesListScreen.kt`
  - `NotesListContent.kt`
  - `NoteItem.kt`
  - `NotesTopBar.kt`
- Split components only when they are reused, visually complex, independently testable, or the file is getting large.
- Avoid splitting tiny private composables into separate files.

Do not:

- call repositories from composables
- keep business logic in composables
- build massive screen files
- spread mutable state across sibling composables
- pass entire state holders or ViewModels deep through the tree
- create "god composables" that own layout, business rules, navigation, and side effects
- use parameter lists that mix unrelated concerns just to avoid extracting components
- hide imperative work in `remember {}` blocks that should live in a state holder
- place deep navigation logic inside reusable components

## Compose Preview Rules

- Every major screen/content/component composable must include `@Preview`.
- Provide previews for both light and dark mode.
- Provide previews for all major states: loading, empty, error, success.
- Provide at least one long-text preview where text truncation/wrapping risk exists.
- Provide multi-device previews when the feature supports those form factors:
  - small phone
  - standard phone
  - tablet/foldable
  - landscape when layout behavior changes
- Provide accessibility text-scale previews when typography density matters.
- Use clear preview naming, for example:
  - `NotesListScreenPreview_Loading`
  - `NotesListScreenPreview_Error`
  - `NotesListScreenPreview_Empty`
  - `NotesListScreenPreview_Success`

## SOLID for Compose UI

- Single Responsibility:
  - one composable should render one coherent piece of UI
  - one state holder should own one screen or one tightly scoped interaction flow
- Open/Closed:
  - extend screens through slots, small wrapper components, or new UI models before editing stable shared components
- Liskov Substitution:
  - composables with the same role should preserve expected behavior when swapped, especially for design-system components
- Interface Segregation:
  - child composables receive narrow props such as `title`, `isPlaying`, `onPlayClick`
  - avoid passing full screen state where a smaller view model object is enough
- Dependency Inversion:
  - composables depend on UI state and callbacks, not concrete repositories, audio engines, nav controllers, or Android services

Reject:

- `ScreenContent(viewModel = ...)` as the default leaf-component API
- components that mutate external state directly instead of raising events
- reusable components that encode feature-specific business policy
- one ViewModel or state holder managing multiple unrelated screens

## SwiftUI Rules

- SwiftUI must mirror the Compose architecture model.
- SwiftUI structure must follow: `View -> ContentView -> Components`.
- `View` owns `@StateObject` and navigation coordination.
- `ContentView` is stateless rendering from immutable `UiState` and closure actions.
- `Components` are reusable medium/large blocks that are independently previewable.
- Use feature-based names, for example:
  - `NotesListView.swift`
  - `NotesListContentView.swift`
  - `NoteRowView.swift`
- Keep views lightweight and use `ObservableObject` (or project standard) for presentation state.
- Follow native Apple navigation and interaction conventions.
- Isolate AVFoundation and WatchConnectivity orchestration from view bodies.
- Avoid over-fragmentation into tiny one-line component files.

Do not:

- default to UIKit when SwiftUI is sufficient
- duplicate shared use-case logic in Swift
- mutate AVAudioSession from multiple unrelated views
- access repositories directly from views/content views
- place business logic in SwiftUI render code
- nest navigation policy deeply in small reusable components

## SwiftUI Preview Rules

- Every major screen/content/component view must include `#Preview` (or `PreviewProvider` where required).
- Provide previews for both light and dark mode.
- Provide previews for all major states: loading, empty, error, success.
- Provide previews that cover dynamic type sizes when text density matters.
- Provide multi-device previews when the feature supports those form factors:
  - small phone (for example iPhone SE class)
  - standard/large phone (for example iPhone Pro class)
  - iPad where supported
- Keep reusable components independently previewable.
- Use clear state-oriented preview naming, for example:
  - `LoadingPreview`
  - `ErrorPreview`
  - `EmptyPreview`
  - `SuccessPreview`

## UI State Management Rules

- UI contract should be `UiState + Action/Event`.
- `UiState` must be immutable and represent the complete render snapshot for a screen.
- `Action` or `Event` types should be explicit, predictable, and testable.
- `ViewModel` translates actions into state transitions through use cases, not repositories directly in UI.

Do not:

- expose mutable domain/data objects directly to UI
- keep hidden mutable state inside reusable components unless truly required for isolated UI behavior
- bypass state holders with ad hoc side effects from components

## Preview Requirements (Mandatory)

- Missing previews are treated as incomplete UI work.
- One happy-path preview is not sufficient.
- All screens/content/components should be previewed against major UI states and theme modes.
- Preview coverage must be kept consistent across Compose and SwiftUI for equivalent features.

Do not:

- skip previews for new UI
- ship only one success preview for stateful screens
- omit dark/light validation
- omit multi-device validation where supported

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

## Mandatory Test Generation Rules

- Every implementation change must include tests or test updates.
- Required test types per feature:
  - unit tests
  - UI tests
  - snapshot tests
  - state rendering tests
  - error-state tests
  - edge-case tests
- If a required test cannot be added, document the reason explicitly in the change summary.

## Unit Test Requirements

- Every public function must be covered by unit tests through public behavior.
- Do not test private functions directly.
- Minimum coverage for public behavior:
  - success path
  - error path
  - empty input
  - invalid input
  - boundary conditions
- Include tests for:
  - mapping functions
  - reducers/state transformers
  - use cases
  - ViewModel actions/events
  - repository behavior with fake data sources

## Compose Testing Rules

- Required for Compose feature work:
  - ViewModel unit tests
  - use case unit tests
  - mapper unit tests
  - Compose UI tests
  - screenshot/snapshot tests when UI changes
- Preferred toolchain:
  - JUnit
  - Kotlin Coroutines Test
  - Turbine for `Flow` testing
  - MockK or fake implementations
  - Compose UI Test
  - Paparazzi or Roborazzi for snapshot testing

## SwiftUI Testing Rules

- Required for SwiftUI feature work:
  - ViewModel unit tests
  - use case unit tests
  - mapper unit tests
  - UI tests with XCTest
  - snapshot tests when UI changes
- Preferred toolchain:
  - XCTest
  - Swift Concurrency testing
  - fake implementations
  - SnapshotTesting or project-approved snapshot tool

## UI Test Requirements

- UI tests must verify:
  - screen loads
  - loading state
  - empty state
  - error state
  - success state
  - user actions trigger expected callbacks/events
  - navigation events are emitted correctly
  - accessibility labels exist for important controls

## Snapshot Test Requirements

- Snapshot tests must cover:
  - light mode
  - dark mode
  - small device
  - large device
  - long text
  - loading state
  - empty state
  - error state
  - success state
- Update snapshots only for intentional visual changes.

## Test File Structure Guidelines

Compose:

```text
feature/notes/
  presentation/
    viewmodel/
    screen/
    components/
  test/
    unit/
    ui/
    snapshot/
```

SwiftUI:

```text
Features/Notes/
  Presentation/
  Tests/
    Unit/
    UI/
    Snapshot/
```

## Forbidden Testing Patterns

- untested public functions
- happy-path-only coverage
- real network/database calls in unit tests
- snapshot tests without state coverage
- UI tests without accessibility selectors/labels
- flaky time-based tests
- direct private-function tests
- over-mocking when a fake is simpler
- missing ViewModel state transition coverage

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
