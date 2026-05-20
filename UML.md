# Vora — UML Reference

All diagrams use [Mermaid](https://mermaid.js.org/) syntax.

---

## 1. Module Dependency Graph

```mermaid
graph TD
    shared["shared\n(KMP: commonMain / androidMain / iosMain)"]

    composeApp["composeApp\n(Android Handheld — Jetpack Compose)"]
    wearApp["wearApp\n(Wear OS — Compose for Wear)"]
    carApp["carApp\n(Android Auto — Car App Library)"]
    iosApp["iosApp\n(iPhone + Apple Watch — SwiftUI)"]

    composeApp --> shared
    wearApp --> shared
    carApp --> shared
    iosApp -.->|"consumes via\nKoin + expect/actual"| shared

    style shared fill:#1e293b,color:#e2e8f0,stroke:#475569
    style composeApp fill:#0f4c81,color:#e2e8f0,stroke:#3b82f6
    style wearApp fill:#134e4a,color:#e2e8f0,stroke:#14b8a6
    style carApp fill:#3b0764,color:#e2e8f0,stroke:#a855f7
    style iosApp fill:#7c2d12,color:#e2e8f0,stroke:#f97316
```

---

## 2. Clean Architecture Layers

```mermaid
graph TD
    subgraph Presentation["Presentation Layer (per platform)"]
        VM["ViewModel / State Owner"]
        Screen["Screen / View"]
        Route["Route / Navigation"]
    end

    subgraph Domain["Domain Layer (shared — commonMain)"]
        UC["Use Cases\n(11 total)"]
        RI["Repository Interfaces\nVoiceMemoRepository\nAudioRecorderRepository\nAudioPlayerRepository"]
        DM["Domain Models\nVoiceMemo · VoiceMemoSource\nRecordingSession · CompletedRecording · PlaybackState"]
    end

    subgraph Data["Data Layer (shared — androidMain / iosMain)"]
        REPO["RoomVoiceMemoRepository\nBootstrapAudioRecorderRepository\nBootstrapAudioPlayerRepository"]
        DAO["VoiceMemoDao"]
        DB["VoraRoomDatabase\n(Room KMP)"]
        ENTITY["VoiceMemoEntity"]
    end

    subgraph Platform["Platform Layer (expect / actual)"]
        PA["getDatabaseBuilder()\ngetRoomDatabase()\ncurrentTimeMillis()\nrandomId()"]
    end

    Screen --> Route
    Route --> VM
    VM --> UC
    UC --> RI
    RI --> DM
    REPO ..|> RI
    REPO --> DAO
    DAO --> ENTITY
    DB --> DAO
    REPO --> DB
    Data --> Platform

    style Presentation fill:#0f4c81,color:#e2e8f0,stroke:#3b82f6
    style Domain fill:#14532d,color:#e2e8f0,stroke:#22c55e
    style Data fill:#3b0764,color:#e2e8f0,stroke:#a855f7
    style Platform fill:#1e293b,color:#e2e8f0,stroke:#475569
```

---

## 3. Core Class Diagram

```mermaid
classDiagram
    %% ── Domain Models ──────────────────────────────────────
    class VoiceMemo {
        +String id
        +String title
        +String audioPath
        +Long durationMs
        +Long createdAt
        +VoiceMemoSource source
        +String? transcript
    }

    class VoiceMemoSource {
        <<enumeration>>
        Phone
        Wear
        Car
    }

    class RecordingSession {
        +String tempPath
        +Long startedAt
    }

    class CompletedRecording {
        +String finalPath
        +Long durationMs
    }

    class PlaybackState {
        <<enumeration>>
        Idle
        Playing
        Paused
    }

    %% ── Repository Interfaces ───────────────────────────────
    class VoiceMemoRepository {
        <<interface>>
        +observeVoiceMemos() Flow~List~VoiceMemo~~
        +getVoiceMemo(id String) VoiceMemo?
        +upsertVoiceMemo(memo VoiceMemo)
        +renameVoiceMemo(id String, title String)
        +deleteVoiceMemo(id String)
    }

    class AudioRecorderRepository {
        <<interface>>
        +startRecording() RecordingSession
        +stopRecording() CompletedRecording
        +cancelRecording()
    }

    class AudioPlayerRepository {
        <<interface>>
        +play(memo VoiceMemo)
        +pause()
        +stop()
    }

    %% ── Use Cases ───────────────────────────────────────────
    class ObserveVoiceMemosUseCase {
        -VoiceMemoRepository repository
        +invoke() Flow~List~VoiceMemo~~
    }
    class GetVoiceMemoUseCase {
        -VoiceMemoRepository repository
        +invoke(id String) VoiceMemo?
    }
    class UpsertVoiceMemoUseCase {
        -VoiceMemoRepository repository
        +invoke(memo VoiceMemo)
    }
    class DeleteVoiceMemoUseCase {
        -VoiceMemoRepository repository
        +invoke(id String)
    }
    class RenameVoiceMemoUseCase {
        -VoiceMemoRepository repository
        +invoke(id String, title String)
    }
    class StartRecordingUseCase {
        -AudioRecorderRepository recorder
        +invoke() RecordingSession
    }
    class StopRecordingUseCase {
        -AudioRecorderRepository recorder
        +invoke() CompletedRecording
    }
    class CancelRecordingUseCase {
        -AudioRecorderRepository recorder
        +invoke()
    }
    class PlayVoiceMemoUseCase {
        -AudioPlayerRepository player
        +invoke(memo VoiceMemo)
    }
    class PausePlaybackUseCase {
        -AudioPlayerRepository player
        +invoke()
    }
    class StopPlaybackUseCase {
        -AudioPlayerRepository player
        +invoke()
    }

    %% ── Data Layer ──────────────────────────────────────────
    class VoiceMemoEntity {
        +String id
        +String title
        +String audioPath
        +Long durationMs
        +Long createdAt
        +String source
        +String? transcript
        +toDomain() VoiceMemo
    }

    class VoiceMemoDao {
        <<interface>>
        +observeAll() Flow~List~VoiceMemoEntity~~
        +getById(id String) VoiceMemoEntity?
        +upsert(entity VoiceMemoEntity)
        +rename(id String, title String)
        +delete(id String)
    }

    class VoraRoomDatabase {
        <<abstract>>
        +voiceMemoDao() VoiceMemoDao
    }

    class RoomVoiceMemoRepository {
        -VoiceMemoDao dao
    }

    class BootstrapAudioRecorderRepository {
    }

    class BootstrapAudioPlayerRepository {
    }

    %% ── DI / Feature Graph ──────────────────────────────────
    class VoiceMemoFeatureUseCases {
        +ObserveVoiceMemosUseCase observe
        +GetVoiceMemoUseCase get
        +UpsertVoiceMemoUseCase upsert
        +DeleteVoiceMemoUseCase delete
        +RenameVoiceMemoUseCase rename
        +StartRecordingUseCase startRecording
        +StopRecordingUseCase stopRecording
        +CancelRecordingUseCase cancelRecording
        +PlayVoiceMemoUseCase play
        +PausePlaybackUseCase pause
        +StopPlaybackUseCase stopPlayback
    }

    class SharedFoundationGraph {
        -SharedFoundationDependencies deps
        +VoiceMemoFeatureUseCases useCases
    }

    class SharedFoundationDependencies {
        <<interface>>
        +VoiceMemoRepository voiceMemoRepository
        +AudioRecorderRepository audioRecorderRepository
        +AudioPlayerRepository audioPlayerRepository
    }

    class NotesFeatureService {
        -VoiceMemoFeatureUseCases useCases
        +observeSnapshot() Flow~NotesSnapshot~
        +rename(id, title)
        +delete(id)
    }

    %% ── Presentation (Android) ──────────────────────────────
    class NotesViewModel {
        -NotesFeatureService service
        +uiState StateFlow~NotesUiState~
        +onRename(id, title)
        +onDelete(id)
    }

    class NotesUiState {
        +NotesScreenMode mode
        +List~NoteItem~ memos
        +List~FilterItem~ filters
        +String summaryText
        +String statusLabel
    }

    %% ── Relationships ───────────────────────────────────────
    VoiceMemo --> VoiceMemoSource

    RoomVoiceMemoRepository ..|> VoiceMemoRepository
    BootstrapAudioRecorderRepository ..|> AudioRecorderRepository
    BootstrapAudioPlayerRepository ..|> AudioPlayerRepository

    RoomVoiceMemoRepository --> VoiceMemoDao
    VoraRoomDatabase --> VoiceMemoDao
    VoiceMemoDao --> VoiceMemoEntity

    ObserveVoiceMemosUseCase --> VoiceMemoRepository
    GetVoiceMemoUseCase --> VoiceMemoRepository
    UpsertVoiceMemoUseCase --> VoiceMemoRepository
    DeleteVoiceMemoUseCase --> VoiceMemoRepository
    RenameVoiceMemoUseCase --> VoiceMemoRepository
    StartRecordingUseCase --> AudioRecorderRepository
    StopRecordingUseCase --> AudioRecorderRepository
    CancelRecordingUseCase --> AudioRecorderRepository
    PlayVoiceMemoUseCase --> AudioPlayerRepository
    PausePlaybackUseCase --> AudioPlayerRepository
    StopPlaybackUseCase --> AudioPlayerRepository

    SharedFoundationDependencies --> VoiceMemoRepository
    SharedFoundationDependencies --> AudioRecorderRepository
    SharedFoundationDependencies --> AudioPlayerRepository

    SharedFoundationGraph --> SharedFoundationDependencies
    SharedFoundationGraph --> VoiceMemoFeatureUseCases

    VoiceMemoFeatureUseCases --> ObserveVoiceMemosUseCase
    VoiceMemoFeatureUseCases --> GetVoiceMemoUseCase
    VoiceMemoFeatureUseCases --> UpsertVoiceMemoUseCase
    VoiceMemoFeatureUseCases --> DeleteVoiceMemoUseCase
    VoiceMemoFeatureUseCases --> RenameVoiceMemoUseCase
    VoiceMemoFeatureUseCases --> StartRecordingUseCase
    VoiceMemoFeatureUseCases --> StopRecordingUseCase
    VoiceMemoFeatureUseCases --> CancelRecordingUseCase
    VoiceMemoFeatureUseCases --> PlayVoiceMemoUseCase
    VoiceMemoFeatureUseCases --> PausePlaybackUseCase
    VoiceMemoFeatureUseCases --> StopPlaybackUseCase

    NotesFeatureService --> VoiceMemoFeatureUseCases
    NotesViewModel --> NotesFeatureService
    NotesViewModel --> NotesUiState
```

---

## 4. Dependency Injection Graph (Koin)

```mermaid
graph TD
    subgraph platformModule["platformModule (actual — Android / iOS)"]
        DB["VoraRoomDatabase"]
    end

    subgraph sharedFoundationModule["sharedFoundationModule (commonMain)"]
        MOCK["VoiceMemoMockFactory"]
        SNAP["NotesSnapshotFactory"]
        REPO_VM["RoomVoiceMemoRepository\nas VoiceMemoRepository"]
        REPO_REC["BootstrapAudioRecorderRepository\nas AudioRecorderRepository"]
        REPO_PLAY["BootstrapAudioPlayerRepository\nas AudioPlayerRepository"]
        DEPS["DefaultSharedFoundationDependencies\nas SharedFoundationDependencies"]
        GRAPH["SharedFoundationGraph"]
        NFS["NotesFeatureService"]
    end

    DB --> REPO_VM
    REPO_VM --> DEPS
    REPO_REC --> DEPS
    REPO_PLAY --> DEPS
    DEPS --> GRAPH
    GRAPH --> NFS

    style platformModule fill:#1e293b,color:#e2e8f0,stroke:#475569
    style sharedFoundationModule fill:#14532d,color:#e2e8f0,stroke:#22c55e
```

---

## 5. expect / actual — Platform Abstractions

```mermaid
graph LR
    subgraph commonMain
        EPM["expect val platformModule"]
        EDB["expect fun getDatabaseBuilder()"]
        ERDB["expect fun getRoomDatabase()"]
        ECT["expect fun currentTimeMillis()"]
        ERI["expect fun randomId()"]
    end

    subgraph androidMain
        APM["actual val platformModule\n→ Room via Context"]
        ADB["actual fun getDatabaseBuilder(ctx)"]
        ARDB["actual fun getRoomDatabase(builder)"]
        ACT["actual fun currentTimeMillis()\n→ System.currentTimeMillis()"]
        ARI["actual fun randomId()\n→ UUID.randomUUID()"]
    end

    subgraph iosMain
        IPM["actual val platformModule\n→ Room via NSHomeDirectory"]
        IDB["actual fun getDatabaseBuilder()"]
        IRDB["actual fun getRoomDatabase(builder)"]
        ICT["actual fun currentTimeMillis()\n→ NSDate().timeIntervalSince1970"]
        IRI["actual fun randomId()\n→ NSUUID().UUIDString()"]
    end

    EPM --> APM
    EPM --> IPM
    EDB --> ADB
    EDB --> IDB
    ERDB --> ARDB
    ERDB --> IRDB
    ECT --> ACT
    ECT --> ICT
    ERI --> ARI
    ERI --> IRI

    style commonMain fill:#1e293b,color:#e2e8f0,stroke:#475569
    style androidMain fill:#0f4c81,color:#e2e8f0,stroke:#3b82f6
    style iosMain fill:#7c2d12,color:#e2e8f0,stroke:#f97316
```