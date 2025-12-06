export enum PreferenceType {
    PASTORAL_VISIT = 'PASTORAL_VISIT',
    BIBLE_STUDY = 'BIBLE_STUDY',
    PRAYER_ONLY = 'PRAYER_ONLY'
}

export enum Urgency {
    HIGH = 'HIGH',
    MEDIUM = 'MEDIUM',
    LOW = 'LOW'
}

export enum RequestStatus {
    PENDING = 'PENDING',
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED'
}

export interface CreatePrayerRequest {
    phone: string;
    district: string;
    preferenceType: PreferenceType;
    urgency: Urgency;
    message?: string;
    wantsNotifications?: boolean;
}

export interface PrayerRequest {
    id: number;
    userId: number;
    userName: string;
    userEmail: string;
    phone: string;
    district: string;
    preferenceType: PreferenceType;
    urgency: Urgency;
    message?: string;
    status: RequestStatus;
    wantsNotifications: boolean;
    createdAt: string;
    updatedAt: string;
    attendedByName?: string;
    attendedAt?: string;
}

export const PREFERENCE_TYPE_LABELS = {
    [PreferenceType.PASTORAL_VISIT]: 'Visita pastoral',
    [PreferenceType.BIBLE_STUDY]: 'Estudio bíblico',
    [PreferenceType.PRAYER_ONLY]: 'Solo oración'
};

export const URGENCY_LABELS = {
    [Urgency.HIGH]: 'Alta',
    [Urgency.MEDIUM]: 'Media',
    [Urgency.LOW]: 'Baja'
};

export const STATUS_LABELS = {
    [RequestStatus.PENDING]: 'Pendiente',
    [RequestStatus.IN_PROGRESS]: 'En proceso',
    [RequestStatus.COMPLETED]: 'Completada',
    [RequestStatus.CANCELLED]: 'Cancelada'
};
