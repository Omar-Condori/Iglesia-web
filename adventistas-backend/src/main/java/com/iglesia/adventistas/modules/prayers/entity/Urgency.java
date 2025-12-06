package com.iglesia.adventistas.modules.prayers.entity;

public enum Urgency {
    HIGH("Alta"),
    MEDIUM("Media"),
    LOW("Baja");

    private final String displayName;

    Urgency(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
