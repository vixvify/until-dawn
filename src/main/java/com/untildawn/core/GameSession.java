package com.untildawn.core;

import java.util.List;
import java.util.Objects;

import com.untildawn.entity.Ghost;
import com.untildawn.entity.ListenerGhost;
import com.untildawn.entity.WandererGhost;
import com.untildawn.entity.WatcherGhost;

public final class GameSession {
    private final NightDefinition night;
    private final List<Ghost> ghosts;

    public GameSession(NightDefinition night, List<Ghost> ghosts) {
        this.night = Objects.requireNonNull(night, "Night must not be null");
        this.ghosts = List.copyOf(ghosts);
        if (this.ghosts.size() != night.ghostCount()) {
            throw new IllegalArgumentException("Ghost roster must match the night definition");
        }
    }

    public static GameSession createSingleNight() {
        return new GameSession(NightDefinition.SINGLE_NIGHT,
                List.of(new WatcherGhost(), new WandererGhost(), new ListenerGhost()));
    }

    public NightDefinition getNight() {
        return night;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public List<String> describePresences() {
        return ghosts.stream().map(Ghost::describePresence).toList();
    }
}
