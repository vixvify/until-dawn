package com.untildawn.entity;

public final class ListenerGhost extends Ghost {
    public ListenerGhost() {
        super("The Listener");
    }

    @Override
    public String describePresence() {
        return getName() + ": Something waits in the silence beyond the door.";
    }
}
