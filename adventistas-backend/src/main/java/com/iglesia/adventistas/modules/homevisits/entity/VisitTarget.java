package com.iglesia.adventistas.modules.homevisits.entity;

public enum VisitTarget {
    MYSELF("A mí"),
    FAMILY("A un familiar"),
    FRIEND("A un amigo"),
    NEIGHBOR("A un vecino"),
    OTHER("Otro");

    private final String displayName;

    VisitTarget(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
