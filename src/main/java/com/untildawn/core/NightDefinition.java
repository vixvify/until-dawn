package com.untildawn.core;

import java.time.LocalTime;

public record NightDefinition(int number, LocalTime startsAt, LocalTime endsAt, int ghostCount) {
    public static final NightDefinition SINGLE_NIGHT = new NightDefinition(1, LocalTime.MIDNIGHT, LocalTime.of(6, 0),
            3);
}
