package com.untildawn.entity;

public final class WatcherGhost extends Ghost {
    public WatcherGhost() {
        super("The Watcher");
    }

    @Override
    public String describePresence() {
        return getName() + ": A silent silhouette stands at the end of the hall.";
    }
}
