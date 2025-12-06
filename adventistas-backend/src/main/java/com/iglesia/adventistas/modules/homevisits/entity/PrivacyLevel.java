package com.iglesia.adventistas.modules.homevisits.entity;

public enum PrivacyLevel {
    PASTOR_ONLY("Solo para el pastor"),
    DISTRICT_LEADER("Puede saberlo el líder de distrito"),
    PRAYER_TEAM("Puede compartirlo el equipo de oración");

    private final String displayName;

    PrivacyLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
