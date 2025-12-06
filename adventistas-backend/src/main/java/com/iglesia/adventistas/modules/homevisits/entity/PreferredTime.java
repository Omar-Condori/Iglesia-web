package com.iglesia.adventistas.modules.homevisits.entity;

public enum PreferredTime {
    MORNING("Mañanas"),
    AFTERNOON("Tardes"),
    EVENING("Noches"),
    COORDINATE_WHATSAPP("Coordinemos por WhatsApp");

    private final String displayName;

    PreferredTime(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
