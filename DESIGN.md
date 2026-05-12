---
version: "alpha"
name: Vora
description: Calm, minimal, voice-first design system for a multiplatform voice memo product.
colors:
  primary: "#14202A"
  secondary: "#5B6B75"
  tertiary: "#2F6B73"
  neutral: "#F4F7F8"
  surface-muted: "#DDEBED"
  danger: "#C95B4A"
  success: "#2F6A4F"
  warning: "#A06A1E"
  on-primary: "#F4F7F8"
  on-secondary: "#F4F7F8"
  on-tertiary: "#F4F7F8"
  on-neutral: "#14202A"
  on-surface-muted: "#14202A"
  on-danger: "#F4F7F8"
typography:
  display:
    fontFamily: system-ui
    fontSize: 2rem
    fontWeight: 700
    lineHeight: 2.375rem
    letterSpacing: -0.02em
  title:
    fontFamily: system-ui
    fontSize: 1.375rem
    fontWeight: 600
    lineHeight: 1.75rem
    letterSpacing: -0.01em
  body:
    fontFamily: system-ui
    fontSize: 1rem
    fontWeight: 400
    lineHeight: 1.5rem
    letterSpacing: 0em
  caption:
    fontFamily: system-ui
    fontSize: 0.8125rem
    fontWeight: 500
    lineHeight: 1.125rem
    letterSpacing: 0.01em
rounded:
  sm: 8px
  md: 14px
  lg: 22px
  pill: 999px
spacing:
  xs: 4px
  sm: 8px
  md: 12px
  lg: 16px
  xl: 24px
  xxl: 32px
components:
  record-button:
    backgroundColor: "{colors.tertiary}"
    textColor: "{colors.on-tertiary}"
    rounded: "{rounded.pill}"
    size: 72px
    typography: "{typography.title}"
  stop-button:
    backgroundColor: "{colors.danger}"
    textColor: "{colors.on-danger}"
    rounded: "{rounded.lg}"
    height: 56px
    padding: "{spacing.lg}"
    typography: "{typography.title}"
  memo-card:
    backgroundColor: "{colors.neutral}"
    textColor: "{colors.on-neutral}"
    rounded: "{rounded.md}"
    padding: "{spacing.lg}"
    typography: "{typography.body}"
  source-chip:
    backgroundColor: "{colors.surface-muted}"
    textColor: "{colors.on-surface-muted}"
    rounded: "{rounded.pill}"
    padding: "{spacing.sm}"
    typography: "{typography.caption}"
  playback-control:
    backgroundColor: "{colors.surface-muted}"
    textColor: "{colors.on-surface-muted}"
    rounded: "{rounded.md}"
    height: 52px
    padding: "{spacing.md}"
    typography: "{typography.body}"
  empty-state:
    backgroundColor: "{colors.neutral}"
    textColor: "{colors.secondary}"
    rounded: "{rounded.md}"
    padding: "{spacing.xl}"
    typography: "{typography.body}"
  sync-status:
    backgroundColor: "{colors.surface-muted}"
    textColor: "{colors.secondary}"
    rounded: "{rounded.pill}"
    padding: "{spacing.sm}"
    typography: "{typography.caption}"
  sync-status-success:
    backgroundColor: "{colors.success}"
    textColor: "{colors.on-primary}"
    rounded: "{rounded.pill}"
    padding: "{spacing.sm}"
    typography: "{typography.caption}"
  sync-status-failure:
    backgroundColor: "{colors.danger}"
    textColor: "{colors.on-danger}"
    rounded: "{rounded.pill}"
    padding: "{spacing.sm}"
    typography: "{typography.caption}"
---

## Overview

Vora should feel quiet, immediate, and trustworthy. The interface exists to help a user capture a thought quickly, confirm it was saved, and replay it later without friction. The visual system should stay calm under repetition: dozens of memos must not make the product feel noisy or heavy.

The brand posture is voice-first rather than library-first. Primary actions should feel immediate, while metadata, sync state, and management actions should feel present but secondary.

## Colors

The palette is built from restrained cool neutrals with one clear action accent and one clear destructive accent.

- **Primary (`#14202A`)** is the default high-contrast text and icon color.
- **Secondary (`#5B6B75`)** is for timestamps, source labels, and other supportive metadata.
- **Tertiary (`#2F6B73`)** is the main active accent for capture and positive interaction emphasis.
- **Neutral (`#F4F7F8`)** is the base surface and background tone.
- **Surface-muted (`#DDEBED`)** supports chips, grouped controls, and passive playback containers.
- **Danger (`#C95B4A`)** is reserved for stop and destructive actions.
- **Success (`#2F6A4F`)** and **Warning (`#A06A1E`)** are status colors only, not primary brand accents.

Use color sparingly. The capture action should stand out because most of the interface remains restrained.

## Typography

Typography should be plainspoken and legible. Vora does not need expressive display typography; it needs clarity at speed across phone, watch, and car contexts.

- `display` is for primary recording headlines and sparse hero moments only.
- `title` is for screen headers, memo titles, and button labels with high action weight.
- `body` is the default scale for metadata and list content.
- `caption` is for timestamps, source chips, and sync indicators.

Favor stable hierarchy over density. Avoid overly compressed text styles on wearable and automotive surfaces.

## Layout

Spacing should bias toward breathable layouts and clear tap separation rather than maximum content density.

- Use `lg` and `xl` for screen-level gaps and section separation.
- Use `md` or `lg` inside cards and grouped controls.
- Keep the primary record action visually isolated from lower-priority controls.
- On watches and car surfaces, reduce simultaneous choices before reducing touch target size.

Large targets matter more than fitting one more line on screen.

## Elevation & Depth

Depth should stay minimal. Vora should not rely on layered shadows or floating chrome to communicate hierarchy.

- Prefer spacing, contrast, and size hierarchy over heavy elevation.
- Use grouped surfaces such as `surface-muted` to separate playback or status elements from the background.
- Reserve stronger emphasis for active recording or destructive confirmation states.

## Shapes

Rounded shapes should signal touchability and calmness.

- `pill` is for chips and circular or near-circular primary controls.
- `lg` is for high-priority buttons such as stop.
- `md` is for cards and grouped controls.
- `sm` is for compact secondary containers only.

The primary record control should feel soft and obvious, not mechanical.

## Components

### Record Button

The record button is the dominant control on capture surfaces. It should appear isolated, large, and unmistakable. Avoid surrounding it with competing actions or decorative framing.

### Stop Button

The stop button must read as immediate and safe. Use the destructive accent consistently so stopping a recording is visually distinct from passive controls.

### Memo Card

Memo cards should prioritize memo name, duration, creation time, and source. Keep the structure quiet and scannable. Do not overload the card with low-value metadata.

### Source Chip

Source chips identify where a memo originated, such as phone, watch, or car. They should be informational, not promotional, and should not steal emphasis from the memo itself.

### Playback Control

Playback controls should communicate transport state clearly. Favor obvious play, pause, and progress behavior over waveform ornamentation that reduces legibility.

### Empty State

Empty states should explain the next action in one sentence. The interface should gently push the user toward capture rather than presenting a feature tour.

### Sync Status

Sync status must be visible without becoming visually dominant. Present status using text plus color so the meaning survives low-attention and color-impaired contexts.

## Do's and Don'ts

Do:

- prioritize fast capture over deep navigation
- keep primary screens focused on one obvious next action
- preserve generous targets on phone, watch, and car surfaces
- keep motion subtle and purposeful
- use platform-native structure while preserving the same product tone

Don't:

- build dense, file-manager-style memo screens
- rely on color alone for recording, sync, or error state
- shrink controls to fit more actions on wearable or automotive surfaces
- translate phone UI directly into watch or car layouts
- use decorative visual noise around the core record and playback actions

## Platform Guidance

Android should use Material 3 patterns with restrained surfaces and immediate access to recording from the default screen. iOS should follow SwiftUI and Apple-native interaction rhythms rather than mirroring Android chrome.

Wear OS and Apple Watch should put capture first, use very short vertical flows, and avoid fine-grained editing. Android Auto should remain playback-first and distraction-safe, with short labels and no reading-heavy management UI. CarPlay later should start from Apple automotive safety constraints and keep scope narrow until the research phase proves more is justified.
