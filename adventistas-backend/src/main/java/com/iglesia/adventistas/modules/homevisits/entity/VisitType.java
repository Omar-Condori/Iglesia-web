package com.iglesia.adventistas.modules.homevisits.entity;

public enum VisitType {
    PASTOR_ONLY("Solo el pastor"),
    PRAYER_TEAM("Equipo de oración"),
    SMALL_GROUP("Grupo pequeño"),
    ANY_REPRESENTATIVE("Cualquier representante de la iglesia");

    private final String displayName;

    VisitType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
