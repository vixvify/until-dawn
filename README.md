# Until Dawn

Until Dawn is a single-player survival horror game for an OOP course: one night, three ghosts, and a security guard on duty from 12 AM to 6 AM.

This version contains the lobby and the foundation for future gameplay. The lobby uses an original dark hallway background, an analog surveillance overlay, and keyboard-accessible menus.

## Tech stack

- Java 21
- JavaFX 21
- Maven
- FXML and JavaFX CSS
- JSON (planned for future save data and settings persistence)

## Requirements

- JDK 21
- Maven 3.9 or later

## Build and run

From the project root, build with:

```sh
mvn clean package
```

Run the application with:

```sh
mvn clean javafx:run
```

The application opens a 1280 × 720 lobby. The composition scales with the window and preserves its 16:9 aspect ratio.

## Current screens and controls

- **NEW GAME:** creates a fresh session with three ghost objects and opens a static Night 01 entry screen. Hover over the shift information to read their individual presence descriptions. No clock, ghost AI, or gameplay runs yet.
- **SETTINGS:** fullscreen and analog visual effects. Changes apply immediately and last for the current application session.
- **Up / Down:** choose a menu item. **Enter / Space:** activate it. **Tab:** move keyboard focus.
- **Esc:** close settings, return from the night screen, or leave fullscreen from the lobby.
- **F11:** toggle fullscreen anywhere.

## Development structure

- `Main`: starts and closes the application.
- `core`: navigation, application preferences, the one-night definition, and the initial game session.
- `controller`: lobby and static night-screen input handling.
- `util`: responsive viewport scaling and disposable visual effects.
- `entity`: the abstract `Ghost` class and three concrete ghost types.
- `world`, `system`: reserved for future locations and game systems.
- `resources/fxml`, `resources/css`, `resources/images`: screen layouts, styling, and artwork.

See [the architecture guide](docs/architecture.md) for the next implementation boundaries and [the artwork record](docs/lobby-art.md) for the generated background and its prompt.

## Four OOP pillars

| Pillar | Demonstration |
| --- | --- |
| Encapsulation | `Ghost` validates its private, final name; `GameSession` copies and protects its ghost roster. |
| Inheritance | `WatcherGhost`, `WandererGhost`, and `ListenerGhost` extend `Ghost` and inherit its identity handling. |
| Abstraction | Abstract `Ghost.describePresence()` defines a contract without prescribing its implementation. |
| Polymorphism | `GameSession.describePresences()` calls the overridden method through `Ghost` references without subtype checks. |

See [the OOP walkthrough](docs/oop.md) for a Thai explanation and the corresponding test cases.

## Tests and CI

```sh
mvn --batch-mode --no-transfer-progress clean verify
```

JUnit tests exercise identity validation, roster protection, fresh ghost instances, the one-night cast, and polymorphic dispatch. These domain tests do not start JavaFX or require a display.

The [Java CI workflow](.github/workflows/ci.yml) runs this command on Windows and Ubuntu using Temurin Java 21. It triggers on pushes, pull requests, and manual dispatch, and caches Maven dependencies. It builds the application and runs domain tests; it does not automate the GUI or publish a release. The workflow becomes active after this file is pushed to GitHub. Manual dispatch is available once it is on the default branch.
