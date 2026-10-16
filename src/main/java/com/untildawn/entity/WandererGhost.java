package com.untildawn.entity;

public final class WandererGhost extends Ghost {
    public WandererGhost() {
        super("The Wanderer");
    }

    @Override
    public String describePresence() {
        return getName() + ": Distant footsteps echo through the empty corridors.";
    }
}
