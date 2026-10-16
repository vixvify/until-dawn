# Until Dawn: lobby and gameplay foundation

## What exists

`Main` creates `SceneManager`, which owns the JavaFX Scene and one shared `AppSettings` instance. It loads FXML screens and releases the outgoing controller's resources before replacing the screen.

`LobbyController` owns menu input and the settings overlay. `SurveillanceEffect` runs a low-contrast, roughly 8 FPS visual animation. Leaving the lobby stops its Timeline and removes settings listeners. Disabling analog effects also stops the Timeline.

`SceneManager.startNewGame()` creates a fresh `GameSession` with `NightDefinition.SINGLE_NIGHT` and three new ghost objects, then passes the session to `NightController`. The definition contains night 1, midnight to 6 AM, and three ghosts. The controller renders a static entry screen and shows the ghosts' descriptions in a tooltip over the shift information. These are setup data and descriptive behavior, not a running simulation.

`Ghost` is an abstract base class with a validated, private name and an abstract `describePresence()` contract. `WatcherGhost`, `WandererGhost`, and `ListenerGhost` inherit the identity handling and override the description. `GameSession` copies its `List<Ghost>` into an unmodifiable roster and calls the common contract polymorphically. See [the OOP walkthrough](oop.md).

`Viewport` scales a fixed 1280 × 720 composition proportionally. Letterboxing prevents clipping when the window has a different aspect ratio. CSS handles presentation, FXML handles layout, and controllers handle UI actions.

`AppSettings` contains fullscreen and visual-effect properties only. Fullscreen stays synchronized with the Stage, and preferences survive screen changes in memory. No settings files are written yet.

## Where gameplay can be added

| Package | Future responsibility | Connection point |
| --- | --- | --- |
| `core` | Extend `GameSession` with game lifecycle and win/loss state | Fresh sessions already originate in `SceneManager.startNewGame()` |
| `entity` | Player and actual ghost behavior | Extend the existing abstract `Ghost` contract when the rules are defined |
| `world` | Office, camera locations, connected rooms | Let the session own the world and expose read-only state to the UI |
| `system` | Night clock, ghost movement, power, encounters | Update the session from one game loop; never reuse the lobby animation timer |
| `controller` | Office and camera input, UI rendering | Replace `night.fxml` or navigate from it to the real game view |
| `resources/data` | JSON night/ghost definitions and save data | Add a loader when these formats are defined |
| `resources/images/ghosts` | Ghost artwork | Keep assets independent of AI classes |
| `src/test/java/com/untildawn` | Extend the existing identity, session and polymorphism tests | Test game systems without requiring a JavaFX window |

Create these classes when their behavior is implemented. The empty package directories are intentionally tracked with `.gitkeep` instead of empty Java classes.

## Lifecycle rules for the next step

1. Keep `NightDefinition` immutable. Keep current time, remaining power, ghost positions, and victory/defeat state in a separate session object.
2. Make `NEW GAME` create fresh session state. Returning to the lobby must stop the game loop and release media, listeners, and timers.
3. Keep ghost decision-making outside FXML controllers so the three ghosts can use different OOP strategies.
4. Add gameplay tests when gameplay is introduced. This scaffold constructs ghost objects but does not place them in a world, advance time, run AI, play audio, manage power, or save progress.
