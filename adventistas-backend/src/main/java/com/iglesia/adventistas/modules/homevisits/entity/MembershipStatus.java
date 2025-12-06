package com.iglesia.adventistas.modules.homevisits.entity;

public enum MembershipStatus {
    YES("Sí"),
    NO("No"),
    PREFER_NOT_SAY("Prefiero no decirlo");

    private final String displayName;

    MembershipStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
