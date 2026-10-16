package com.untildawn.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.untildawn.entity.Ghost;
import com.untildawn.entity.ListenerGhost;
import com.untildawn.entity.WandererGhost;
import com.untildawn.entity.WatcherGhost;
import org.junit.jupiter.api.Test;

class GameSessionTest {
    @Test
    void singleNightHasExactlyTheThreeDistinctGhostTypes() {
        GameSession session = GameSession.createSingleNight();

        assertEquals(1, session.getNight().number());
        assertEquals(LocalTime.MIDNIGHT, session.getNight().startsAt());
        assertEquals(LocalTime.of(6, 0), session.getNight().endsAt());
        assertEquals(3, session.getNight().ghostCount());
        assertEquals(3, session.getGhosts().size());
        assertEquals(Set.of(WatcherGhost.class, WandererGhost.class, ListenerGhost.class),
                session.getGhosts().stream().map(Object::getClass).collect(Collectors.toSet()));
        assertEquals(3, new HashSet<>(session.describePresences()).size());
        assertTrue(session.describePresences().stream().noneMatch(String::isBlank));
    }

    @Test
    void newGamesDoNotReuseGhostInstances() {
        GameSession first = GameSession.createSingleNight();
        GameSession second = GameSession.createSingleNight();

        assertNotSame(first, second);
        for (int index = 0; index < first.getGhosts().size(); index++) {
            assertNotSame(first.getGhosts().get(index), second.getGhosts().get(index));
        }
    }

    @Test
    void rosterCannotBeChangedThroughTheInputOrGetter() {
        List<Ghost> supplied = new ArrayList<>(GameSession.createSingleNight().getGhosts());
        GameSession session = new GameSession(NightDefinition.SINGLE_NIGHT, supplied);
        supplied.clear();

        assertEquals(3, session.getGhosts().size());
        assertThrows(UnsupportedOperationException.class, () -> session.getGhosts().clear());
    }

    @Test
    void rejectsRosterThatDisagreesWithTheNightDefinition() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameSession(NightDefinition.SINGLE_NIGHT, List.of(new WatcherGhost())));
    }

    @Test
    void rejectsNullDefinitionRosterAndGhosts() {
        assertThrows(NullPointerException.class, () -> new GameSession(null, List.of()));
        assertThrows(NullPointerException.class, () -> new GameSession(NightDefinition.SINGLE_NIGHT, null));
        assertThrows(NullPointerException.class, () -> new GameSession(NightDefinition.SINGLE_NIGHT,
                Arrays.asList(new WatcherGhost(), null, new ListenerGhost())));
    }

    @Test
    void dispatchesToAnAdditionalSubtypeWithoutChangingSessionCode() {
        Ghost additionalType = new Ghost("A fourth type for this test only") {
            @Override
            public String describePresence() {
                return "The subtype chooses this description.";
            }
        };
        NightDefinition testNight = new NightDefinition(1, LocalTime.MIDNIGHT, LocalTime.of(6, 0), 1);
        GameSession session = new GameSession(testNight, List.of(additionalType));

        assertEquals(List.of("The subtype chooses this description."), session.describePresences());
    }
}
