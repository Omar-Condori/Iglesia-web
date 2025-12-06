package com.iglesia.adventistas.modules.prayers.entity;

public enum RequestStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En proceso"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada");

    private final String displayName;

    RequestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
