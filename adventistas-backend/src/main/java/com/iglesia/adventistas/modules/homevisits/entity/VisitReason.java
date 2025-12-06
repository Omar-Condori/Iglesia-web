package com.iglesia.adventistas.modules.homevisits.entity;

public enum VisitReason {
    HOME_PRAYER("Oración en casa"),
    BIBLE_STUDY("Estudio bíblico en familia"),
    PASTORAL_VISIT("Visita pastoral"),
    SPIRITUAL_COUNSELING("Consejería espiritual"),
    HOME_BLESSING("Bendición del hogar"),
    PRAYER_FOR_SICK("Oración por enfermos"),
    EMOTIONAL_SUPPORT("Acompañamiento emocional"),
    OTHER("Otro");

    private final String displayName;

    VisitReason(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
