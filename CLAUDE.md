# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

- **Build:** `./gradlew build`
- **All unit tests:** `./gradlew test`
- **Single module tests:** `./gradlew :feature:user:test`
- **Single test class:** `./gradlew test --tests "*.UserViewModelTest"`
- **Debug build (Fastlane):** `fastlane build_debug`

No lint gate is enforced; `./gradlew lint` runs Android lint but is not blocking.

## Architecture

Multi-module MVVM + Jetpack Compose + Hilt (kapt, not ksp).

**Modules:** `:app`, `:core:{common,domain,data,network,model,designsystem}`, `:feature:user`

**Data flow:**
```
UI (Compose) → ViewModel → UseCase → Repository interface (core:domain)
  → RepositoryImpl (core:data) → NetworkDataSource (core:network) → Retrofit + Moshi
```

**UI state pattern:** Each feature uses a `sealed interface XxxUiState` with `Loading`, `EmptyList`, `Success`, and `Error` variants. The ViewModel exposes a `StateFlow<XxxUiState>` via `stateIn(WhileSubscribed(5_000))`.

**UseCase result pattern:** UseCases return `Flow<XxxResult>` using sealed classes (`Success`, `Failure`, `EmptyList`). Errors map to domain-level sealed classes (e.g. `UserError.NetworkUnavailable`, `UserError.Unknown`).

**Entrypoint:** `BaaApplication` (`@HiltAndroidApp`) → `MainActivity` (`@AndroidEntryPoint`) → `BaaApp` composable → `BaaNavHost` with sealed `Screen` classes per feature.

**API config:** BASE_URL, API_PRIVATE, API_PUBLIC are set as `buildConfigField` in `core/network/build.gradle.kts`.

## Dependency Injection

- Hilt `@Binds` modules: `NetworkModule`, `DataModule` (all `SingletonComponent`)
- `RetrofitModule` provides `OkHttpClient` via `@Provides`
- Dispatcher qualifiers defined in `:core:common`: `@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`, `@UnconfinedDispatcher` — always inject these instead of using `Dispatchers.*` directly

## Testing

- JUnit 4 + MockK + `kotlinx-coroutines-test`
- `MainDispatcherRule` (uses `UnconfinedTestDispatcher`) — apply with `@get:Rule` in every ViewModel test
- Prefer fake repositories (e.g. `TestUserRepository`) over mocks for simple cases; use MockK for complex interactions
- Shared test data in `feature/user/src/test/.../data/UserTestData.kt`
- Use `kotlin.test` assertions (`assertEquals`, `assertIs`) alongside JUnit assertions

## Key Versions

AGP 8.1.1 · Kotlin 2.0.0 · Gradle 8.5 · Java 17 · minSdk 26 · compileSdk 34 · Compose BOM 2025.01.01 · Hilt 2.47 · Retrofit 2.9.0 · Moshi 1.12.0

## Worktrees

This project runs inside a Docker container where the repo is mounted at `/workspace`. The host macOS path is `/Users/luiseduardo/Projetos/Android/pokerhoursandroidapp/pokerhoursandroidapp`.

When creating a git worktree, git records the container path (`/workspace/...`) in the gitdir files, breaking the link on the host. **Always fix both sides after creating a worktree:**

```bash
# 1. Create the worktree
git worktree add worktrees/<name> -b <branch-name>

# 2. Fix .git inside the worktree (container path → host path)
echo "gitdir: /Users/luiseduardo/Projetos/Android/pokerhoursandroidapp/pokerhoursandroidapp/.git/worktrees/<name>" > worktrees/<name>/.git

# 3. Fix gitdir inside main .git (host path → worktree)
echo "/Users/luiseduardo/Projetos/Android/pokerhoursandroidapp/pokerhoursandroidapp/worktrees/<name>/.git" > .git/worktrees/<name>/gitdir
```

Verify with `git worktree list` — the worktree must show the full macOS host path, not `/workspace/...`.

## Conventions

- Base package: `com.example.baseandroidapp`
- Annotation processing uses `kotlin-kapt` with `correctErrorTypes = true` — do not switch to KSP
