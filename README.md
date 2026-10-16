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

- **NEW GAME:** opens a static Night 01 entry screen. This is the navigation boundary for the future game; no clock, ghost AI, or gameplay runs yet.
- **SETTINGS:** fullscreen and analog visual effects. Changes apply immediately and last for the current application session.
- **Up / Down:** choose a menu item. **Enter / Space:** activate it. **Tab:** move keyboard focus.
- **Esc:** close settings, return from the night screen, or leave fullscreen from the lobby.
- **F11:** toggle fullscreen anywhere.

## Development structure

- `Main`: starts and closes the application.
- `core`: navigation, application preferences, and the one-night definition.
- `controller`: lobby and static night-screen input handling.
- `util`: responsive viewport scaling and disposable visual effects.
- `entity`, `world`, `system`: reserved for future game objects, locations, and game systems.
- `resources/fxml`, `resources/css`, `resources/images`: screen layouts, styling, and artwork.

See [the architecture guide](docs/architecture.md) for the next implementation boundaries and [the artwork record](docs/lobby-art.md) for the generated background and its prompt.
