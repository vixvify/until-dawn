package com.untildawn.entity;

import java.util.Objects;

public abstract class Ghost {
    private final String name;

    protected Ghost(String name) {
        Objects.requireNonNull(name, "Ghost name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Ghost name must not be blank");
        }
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract String describePresence();
}
