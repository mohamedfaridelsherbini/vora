# Vora Roadmap

## Phase 1. Android Local MVP

**Goal**

Prove the local handheld voice memo loop on Android.

**Included features**

- record
- stop
- save local file
- list memos
- play memo
- delete memo
- rename memo

**Excluded features**

- cloud sync
- transcription
- wearable sync
- automotive recording

**Acceptance criteria**

- local recording and playback are stable
- memo list persists across restarts
- delete and rename are reliable
- app is usable offline

## Phase 2. iOS Local MVP

**Goal**

Bring the same core local memo loop to iPhone with Apple-native UX.

**Included features**

- AVFoundation recording
- local memo list
- playback
- rename
- delete

**Excluded features**

- watch sync
- cloud sync
- transcription

**Acceptance criteria**

- iPhone recording and playback work reliably
- memo persistence survives app restart
- shared business rules are reused rather than rewritten

## Phase 3. Wear OS Quick Recording

**Goal**

Enable fast wrist capture on Wear OS.

**Included features**

- quick record entry
- stop and save locally or queue handoff depending on final design
- minimal recent memo access if lightweight

**Excluded features**

- dense memo management
- advanced editing
- transcription

**Acceptance criteria**

- capture can start in a few seconds
- wearable UX is shallow and battery-aware
- no phone dependency blocks local capture flow

## Phase 4. Apple Watch Quick Recording

**Goal**

Enable native Apple Watch quick capture with watch-appropriate interaction.

**Included features**

- quick record flow
- stop and save behavior
- later-ready sync seams

**Excluded features**

- full memo management console
- cloud sync
- transcription

**Acceptance criteria**

- watch flow is glanceable and one-hand friendly
- Apple-native interaction patterns are preserved
- connectivity failure does not break local capture

## Phase 5. Android Auto Playback

**Goal**

Expose safe playback-first access in Android Auto.

**Included features**

- browse playable memos
- start, pause, resume, seek if safe and supported
- playback-oriented voice-safe controls

**Excluded features**

- recording
- renaming
- dense list management

**Acceptance criteria**

- automotive UI is distraction-safe
- playback works through approved automotive patterns
- nonessential management remains on phone

## Phase 6. AI Transcription

**Goal**

Add transcript generation without degrading the core memo loop.

**Included features**

- transcript job pipeline
- transcript storage model
- transcript display states

**Excluded features**

- summarization beyond agreed scope
- cloud sync unless already delivered

**Acceptance criteria**

- recording and playback remain fast
- transcription failure does not block memo availability
- transcript state is explicit and recoverable

## Phase 7. Cloud Sync

**Goal**

Synchronize memos and metadata across devices.

**Included features**

- sync protocol
- conflict policy
- upload and download flow
- offline queueing and retry

**Excluded features**

- broad collaboration features
- uncontrolled background battery use

**Acceptance criteria**

- offline-first behavior is preserved
- sync state is observable
- conflict handling is deterministic

## Phase 8. CarPlay Research

**Goal**

Decide whether and how CarPlay should ship without harming safety or team focus.

**Included features**

- feasibility study
- Apple automotive constraints review
- playback-first UX proposal

**Excluded features**

- full implementation commitment before research sign-off

**Acceptance criteria**

- clear recommendation exists
- scope, risk, and required Apple platform work are documented
- safety and maintenance cost are understood
