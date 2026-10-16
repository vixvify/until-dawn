package com.untildawn.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GhostTest {
    @Test
    void sharedConstructorPreservesValidIdentity() {
        Ghost ghost = new TestGhost("A new ghost");
        assertEquals("A new ghost", ghost.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t\n"})
    void sharedConstructorRejectsBlankNames(String name) {
        assertThrows(IllegalArgumentException.class, () -> new TestGhost(name));
    }

    @Test
    void sharedConstructorRejectsNullName() {
        assertThrows(NullPointerException.class, () -> new TestGhost(null));
    }

    private static final class TestGhost extends Ghost {
        private TestGhost(String name) {
            super(name);
        }

        @Override
        public String describePresence() {
            return "A test presence";
        }
    }
}
