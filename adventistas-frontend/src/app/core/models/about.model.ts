export interface AboutSection {
    id: number;
    slug: string;
    title: string;
    content: string;
    icon: string;
    sortOrder: number;
    isActive: boolean;
    createdAt?: string;
    updatedAt?: string;
}

export interface UpdateAboutSectionRequest {
    title: string;
    content?: string;
    icon?: string;
    sortOrder?: number;
    isActive?: boolean;
}
