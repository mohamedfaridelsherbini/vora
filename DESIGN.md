---
product:
  name: Vora
  identity: calm-minimal-voice-first
principles:
  - fast-capture
  - calm-interface
  - large-touch-targets
  - minimal-navigation
  - accessible-contrast
  - watch-friendly-layout
  - car-safe-layout
colors:
  ink:
    value: "#14202A"
    usage: primary-text
  mist:
    value: "#F4F7F8"
    usage: app-background
  slate:
    value: "#5B6B75"
    usage: secondary-text
  tide:
    value: "#2F6B73"
    usage: primary-action
  foam:
    value: "#DDEBED"
    usage: secondary-surface
  coral:
    value: "#C95B4A"
    usage: destructive-action
  pine:
    value: "#2F6A4F"
    usage: success-state
  amber:
    value: "#A06A1E"
    usage: warning-state
typography:
  display:
    fontFamily: system-sans
    fontSize: 32
    fontWeight: 700
    lineHeight: 38
  title:
    fontFamily: system-sans
    fontSize: 22
    fontWeight: 600
    lineHeight: 28
  body:
    fontFamily: system-sans
    fontSize: 16
    fontWeight: 400
    lineHeight: 24
  caption:
    fontFamily: system-sans
    fontSize: 13
    fontWeight: 500
    lineHeight: 18
spacing:
  xs: 4
  sm: 8
  md: 12
  lg: 16
  xl: 24
  xxl: 32
radius:
  sm: 8
  md: 14
  lg: 22
  pill: 999
components:
  recordButton:
    shape: circle
    minSize: 72
    background: tide
    foreground: mist
  stopButton:
    shape: rounded-rectangle
    minHeight: 56
    background: coral
    foreground: mist
  memoCard:
    background: mist
    foreground: ink
    borderRadius: md
    padding: lg
  sourceChip:
    background: foam
    foreground: slate
    borderRadius: pill
    paddingX: md
    paddingY: sm
  playbackControl:
    minHeight: 52
    background: foam
    foreground: ink
  emptyState:
    background: mist
    foreground: slate
  syncStatus:
    idle: slate
    syncing: tide
    success: pine
    failure: coral
---

# Vora Design

## Intent

Vora should feel quiet, immediate, and trustworthy. The interface serves the act of capturing and replaying a thought, not managing a media library.

## Core Principles

- Capture must dominate the layout.
- Primary screens should have one obvious next action.
- Visual noise should stay low even when memo density grows.
- Status should be visible without becoming theatrical.
- Touch targets must remain generous across handheld, wrist, and automotive surfaces.

## Token Guidance

### Colors

- `ink` is the default text and icon color.
- `mist` is the default surface and background base.
- `tide` is reserved for primary action and active playback or recording emphasis.
- `coral` is reserved for destructive and stop actions.
- `foam` supports chips, passive controls, and grouped surfaces.

### Typography

- Use `display` for main recording headlines only.
- Use `title` for screen titles and memo card names.
- Use `body` for metadata and supporting text.
- Use `caption` for timestamps, source labels, and sync state.

### Spacing And Radius

- Use `lg` or `xl` for primary layout gaps.
- Use `md` spacing inside cards and controls.
- Use `pill` radius for chips only.
- Use `lg` radius for highly touchable surfaces.

## Components

### Record Button

- Must be the dominant control on capture surfaces.
- Use a circular shape with strong contrast.
- Do not surround it with competing secondary actions.

### Stop Button

- Must read as immediate and safe.
- Use `coral` consistently.
- Keep label short and explicit.

### Memo Card

- Show title, duration, created time, and source with clear hierarchy.
- Keep the card tappable as one unit unless playback controls are embedded.
- Avoid dense metadata blocks.

### Source Chip

- Use for `Phone`, `Watch`, `Car`, or future sync provenance.
- Keep chips quiet, not promotional.

### Playback Control

- Favor clear transport states over decorative waveform-first UI.
- Maintain large hit areas and obvious active state.

### Empty State

- Explain the next useful action in one sentence.
- Keep illustration optional; copy should do the work.

### Sync Status

- Must be visible but low prominence.
- Represent state with color and text, not color alone.

## Platform Guidance

### Android

- Use Material 3 structure, but keep surfaces minimal.
- Prefer bottom-level navigation only if more than one primary section survives scope review.
- Recording should be reachable immediately from the default screen.

### iOS

- Use SwiftUI navigation and Apple spacing rhythm.
- Keep top-level actions obvious and avoid Android-style chrome translation.
- Respect Apple permission and audio-session affordances.

### Wear OS

- Put capture first.
- Use large centered actions and short vertical flows.
- Limit on-screen metadata to essentials.

### Apple Watch

- Favor glanceable status and quick actions.
- Keep text short and avoid fine-grained editing on watch.
- Do not depend on paired phone connectivity for the primary capture interaction.

### Android Auto

- Prioritize playback and safe resumption.
- Use large templates and short labels.
- Do not introduce reading-heavy memo management.

### CarPlay Later

- Start from Apple automotive safety constraints, not phone parity.
- Keep scope playback-first until research proves more is justified.

## Accessibility

- Maintain accessible contrast for text and actionable controls.
- Do not rely on color alone for recording, sync, or error state.
- Preserve generous hit targets on all primary controls.
- Keep motion subdued and purposeful.
