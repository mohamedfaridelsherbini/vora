# Vora Agent Manual

## Purpose

This file defines how coding agents operate in Vora. It is a delivery contract for architecture, ownership, and review discipline across Android, iOS, wearable, and automotive surfaces.

## Architecture Diagrams

Full UML reference — module graph, clean architecture layers, class diagram, DI graph, and expect/actual map — lives in **[UML.md](./UML.md)**.

Diagrams use Mermaid syntax and render natively in GitHub, JetBrains IDEs, and VS Code (Markdown Preview Mermaid Support).

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

## UI Structure Contract

All platform UI must use the same responsibility split. Do not keep routing, state, layout, and leaf components in one file once a screen is beyond trivial bootstrap code.

### Unified feature architecture

Every feature should follow this architecture:

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

This same mental model must be mirrored across Compose and SwiftUI even when syntax differs.

### Required screen split

| Layer | Responsibility | Android / Wear / Car example | iOS / watchOS example |
| --- | --- | --- | --- |
| App entry | platform entry point only | `App.kt`, `WearEntryActivity.kt`, `CarEntryActivity.kt` | `ContentView.swift`, `WatchRecordView.swift` |
| Route | startup handoff, navigation choice, state-owner binding | `NotesRoute.kt`, `CaptureRoute.kt`, `HomeRoute.kt` | `NotesRoute.swift`, `WatchCaptureRoute.swift` |
| UI state | immutable screen state and visual state types | `NotesUiState.kt`, `SplashUiState.kt`, `HomeUiState.kt` | `NotesModels.swift` |
| Screen | top-level feature layout and section composition | `NotesScreen.kt`, `CaptureScreen.kt`, `HomeScreen.kt` | `NotesScreen.swift`, `WatchCaptureScreen.swift` |
| Components | reusable feature-level UI parts | `NotesComponents.kt` | `NotesComponents.swift` |
| Theme | tokens, spacing, colors, typography | `presentation/theme/` | `presentation/theme/` |

### Feature-first UI folders

- Android mobile: `composeApp/src/androidMain/kotlin/.../presentation/<feature>/`
- iPhone: `iosApp/iosApp/presentation/<feature>/`
- Wear OS: `wearApp/src/main/java/.../presentation/<feature>/`
- Apple Watch: `iosApp/VoraWatch Watch App/presentation/<feature>/`
- Car: `carApp/src/main/java/.../presentation/<feature>/`

### Current expected structure

- Android mobile:
  - `presentation/splash/`
  - `presentation/notes/`
- iPhone:
  - `presentation/splash/`
  - `presentation/notes/`
- Wear OS:
  - `presentation/capture/`
- Apple Watch:
  - `presentation/capture/`
- Car:
  - `presentation/home/`

### UI file rules

- Entry files may host platform bootstrapping only. They must not contain full page UI trees.
- Route files may orchestrate timing, navigation, and state selection. They must not become rendering-heavy.
- One screen file should contain the screen structure only.
- Reusable or complex UI parts should be split into separate files.
- Screen files compose sections and own page-level layout only.
- Components files own headers, cards, chips, search bars, buttons, and other reusable feature UI parts.
- UI state files own immutable screen contracts. Do not bury state types inside unrelated files.
- If a page starts mixing route logic, state, and reusable sections, split it before adding more behavior.

### Compose and SwiftUI parity rules

- Compose flow must follow: `Route -> Screen -> Content -> Components`.
- SwiftUI flow must follow: `View -> ContentView -> Components`.
- Route/View layers wire state owners and navigation only.
- Screen/Content layers render from immutable `UiState` and explicit actions/events.
- Reusable components must not own business rules or deep navigation policy.
- Split files when UI is reusable, visually complex, independently testable, or the screen file is becoming large.
- Avoid over-splitting tiny private text/button wrappers into separate files.

### Forbidden UI architecture patterns

- Massive screen/view files (especially 500+ lines without decomposition)
- Business logic inside composables or SwiftUI views
- Repository/data-source usage inside UI rendering layers
- ViewModel injection into reusable leaf components by default
- Deep navigation logic nested inside small UI components
- Splitting every tiny primitive into its own file
- Missing previews for major UI states
- Only one happy-path preview on stateful screens
- No dark/light preview coverage
- No multi-device previews where a feature supports multiple form factors

### State and action contract

- Default contract is `UiState + Action/Event`.
- `UiState` must be immutable and represent full render state for the screen.
- Actions/events must be explicit and flow through screen state owners.
- Screen/view files orchestrate structure only; they should not mutate business state directly.

### Preview standards (mandatory)

- Generate previews for every major screen, content layer, and reusable component.
- Minimum state previews: loading, empty, error, success.
- Include both dark and light mode previews.
- Include long-text previews where text clipping/wrapping is possible.
- Include accessibility previews (font scaling/dynamic type) when typography density matters.
- Multi-device previews are required when the feature supports those form factors:
  - small phone
  - standard/large phone
  - tablet/foldable
  - landscape when layout behavior changes

Compose preview expectations:

- Use `@Preview` for all major composables.
- Use additional `@Preview` variants for device and night mode when relevant.
- Use explicit preview names such as `FeatureScreenPreview_Loading` and `FeatureScreenPreview_Error`.

SwiftUI preview expectations:

- Use `#Preview` or `PreviewProvider` based on target compatibility.
- Include state-specific and device-specific previews.
- Use explicit preview names such as `LoadingPreview`, `EmptyPreview`, `ErrorPreview`, `SuccessPreview`.

### AI generation sequence

When generating UI, agents must build in this order:

1. Feature folder structure
2. Route/View entry
3. Screen/Content rendering layer
4. Reusable components
5. ViewModel + state + actions
6. Previews for states, themes, accessibility, and supported devices

Agents must never generate one-file UI implementations that mix route, rendering, business rules, and data access.

## Testing Requirements (Mandatory)

All agents must generate or update tests with every implementation change.

Required test types per feature:

- Unit tests
- UI tests
- Snapshot tests
- State rendering tests
- Error-state tests
- Edge-case tests

### Unit test contract

- Every public function must be covered by tests through public behavior.
- Never test private functions directly.
- Minimum behavior coverage:
  - success path
  - error path
  - empty input
  - invalid input
  - boundary cases
- Must cover:
  - mappers
  - reducers/state transition logic
  - use cases
  - ViewModel action/event handling
  - repository behavior using fake data sources

### Compose test contract

- Add/update:
  - ViewModel unit tests
  - use case unit tests
  - mapper unit tests
  - Compose UI tests
  - snapshot tests when UI changes
- Prefer:
  - JUnit
  - Kotlin Coroutines Test
  - Turbine
  - MockK or fakes
  - Compose UI Test
  - Paparazzi or Roborazzi

### SwiftUI test contract

- Add/update:
  - ViewModel unit tests
  - use case unit tests
  - mapper unit tests
  - UI tests with XCTest
  - snapshot tests when UI changes
- Prefer:
  - XCTest
  - Swift Concurrency testing
  - fake implementations
  - SnapshotTesting or approved project snapshot tool

### UI and snapshot expectations

- UI tests must verify loading, empty, error, and success states.
- UI tests must verify user actions trigger expected callbacks/events.
- UI tests must verify navigation events where applicable.
- UI tests must verify accessibility labels for important controls.
- Snapshot tests must cover light/dark, small/large device, long text, and all major UI states.
- Snapshots are updated only when the visual change is intentional.

### Test generation behavior

When implementing or modifying code, agents must:

1. Add or update unit tests.
2. Add or update UI tests.
3. Add or update snapshot tests when UI changes.
4. Cover all `UiState` cases.
5. Cover all action/event handling paths.
6. Use fake dependencies by default.
7. Prefer behavior-based assertions over implementation-detail assertions.
8. Explain explicitly when a required test cannot be added.

### Forbidden testing patterns

- untested public functions
- happy-path-only tests
- real network/database in unit tests
- snapshot coverage that misses state variants
- UI tests without accessibility selectors
- flaky time-based tests
- private-function testing
- over-mocking when a fake is simpler
- missing ViewModel state transition assertions

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
- Enforce SOLID structure inside Compose UI trees and screen state holders

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
- Is the route/container/content split clear where the screen is non-trivial?
- Does the feature follow `presentation/<feature>/Route + UiState + Screen + Components` instead of a single large page file?
- Does each composable have one rendering responsibility?
- Are child composable parameters narrow and role-specific instead of whole-screen objects by default?
- Can design-system or leaf components be previewed without constructing app dependencies?

**Anti-patterns**

- massive composables
- repository calls from UI
- giant ViewModels
- direct feature-to-feature UI coupling
- route composables that also perform rendering, data access, and navigation decisions inline
- leaf composables that accept a ViewModel or repository just for convenience
- shared UI components that embed feature business rules

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
- Does the feature use `Route + Models/UiState + Screen + Components` instead of a giant SwiftUI file?

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
