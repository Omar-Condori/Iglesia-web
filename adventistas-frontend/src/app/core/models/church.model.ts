// Modelo de iglesia
export interface Church {
  id: number;
  name: string;
  slug: string;
  description?: string;
  address: string;
  city: string;
  state: string;
  postalCode?: string;
  country: string;
  phone?: string;
  email?: string;
  website?: string;
  latitude?: number;
  longitude?: number;
  imageUrl?: string;
  pastor?: string;
  foundedYear?: number;
  membersCount?: number;
  serviceSchedule?: string;
  isActive: boolean;
  unionName: string;
  createdAt: string;
}

export interface CreateChurchRequest {
  name: string;
  description?: string;
  address: string;
  city: string;
  state: string;
  postalCode?: string;
  country: string;
  phone?: string;
  email?: string;
  website?: string;
  latitude?: number;
  longitude?: number;
  pastor?: string;
  foundedYear?: number;
  membersCount?: number;
  serviceSchedule?: string;
  imageUrl?: string;
  unionId?: number;
}
