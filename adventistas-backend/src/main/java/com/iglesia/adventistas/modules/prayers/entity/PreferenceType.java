package com.iglesia.adventistas.modules.prayers.entity;

public enum PreferenceType {
    PASTORAL_VISIT("Visita pastoral"),
    BIBLE_STUDY("Estudio bíblico"),
    PRAYER_ONLY("Solo oración");

    private final String displayName;

    PreferenceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
