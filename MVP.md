# Vora MVP

## MVP 1 Goal

Deliver a buildable Android mobile app that proves the core local voice memo loop end to end.

## Included Scope

- Android mobile only
- start recording a voice memo
- stop recording
- save a local audio file
- list saved memos
- play a memo
- delete a memo
- rename a memo

## Excluded Scope

- backend services
- cloud sync
- AI transcription
- authentication
- Apple Watch sync
- Wear OS sync
- CarPlay
- Android Auto recording
- cross-device memo transfer
- collaborative or shared memos

## User Flows

### Capture

1. User opens the Android app.
2. User starts recording with one primary action.
3. User stops recording.
4. App persists audio locally and creates a memo entry.

### Browse

1. User views locally saved memos.
2. User sees title, duration, and created time.

### Playback

1. User taps a memo.
2. User plays and stops playback reliably.

### Manage

1. User renames a memo.
2. User deletes a memo and its local file safely.

## Non-Goals

- background sync
- remote storage
- transcript editing
- watch capture
- automotive capture

## Acceptance Criteria

- Android app can record and save a real audio file locally.
- Saved memos remain available after app restart.
- Playback works for every saved memo.
- Delete removes both memo metadata and local file reference safely.
- Rename updates the visible title without corrupting the audio file path.
- Main recording and playback paths work offline.

## Architecture Requirements For MVP

- business rules live in `shared`
- Android-specific recording and playback live in Android code
- UI state is immutable
- no business logic in composables
- local persistence is replaceable behind repository contracts
