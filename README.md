# Vora

Vora is a multiplatform voice memo app focused on fast capture, calm playback, and native behavior across handheld, wearable, and automotive surfaces.

## Supported Platforms

- Android mobile
- iPhone
- Wear OS
- Apple Watch
- Android Auto
- CarPlay later

## Architecture Summary

- `shared` holds business models, repository contracts, use cases, and shared rules.
- `composeApp` is the Android mobile application.
- `iosApp` contains iPhone and Apple Watch native entry points.
- `wearApp` contains Wear OS-specific UI and integrations.
- `carApp` contains Android Auto playback-first work.

## Current Status

The repository is in foundation setup. Architecture, rules, roadmap, and design guidance are defined before feature implementation.

## Setup Notes

- Android builds use Gradle from the project root.
- iOS and Apple Watch targets are managed through Xcode in `iosApp`.
- Local platform SDK setup is still required for running apps on devices or simulators.

## Development Priorities

1. Fast local capture
2. Reliable local playback
3. Offline-first storage
4. Native UX on each platform
5. Clean cross-platform architecture

## AI Project Files

- [AGENTS.md](./AGENTS.md): agent ownership and review contract
- [RULES.md](./RULES.md): enforceable engineering rules
- [ARCHITECTURE.md](./ARCHITECTURE.md): module boundaries and dependency direction
- [MVP.md](./MVP.md): first buildable scope
- [ROADMAP.md](./ROADMAP.md): phased delivery plan
- [DESIGN.md](./DESIGN.md): design tokens and interaction guidance

## Build Notes

Examples:

```bash
./gradlew :composeApp:assembleDebug
./gradlew :wearApp:assembleDebug
./gradlew :carApp:assembleDebug
```

Open `iosApp` in Xcode for iPhone and Apple Watch targets.
