export interface UserDepartment {
    id: number;
    userId: number;
    departmentId: number;
    departmentName: string;
    departmentSlug: string;
    canView: boolean;
    canEdit: boolean;
    canPublish: boolean;
}

export interface AssignDepartmentRequest {
    departmentId: number;
    canView: boolean;
    canEdit: boolean;
    canPublish: boolean;
}
