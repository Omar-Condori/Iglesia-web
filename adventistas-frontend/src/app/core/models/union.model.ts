// Modelo de unión
export interface Union {
  id: number;
  name: string;
  slug: string;
  description?: string;
  address: string;
  city: string;
  state: string;
  country: string;
  phone?: string;
  email?: string;
  website?: string;
  logoUrl?: string;
  isActive: boolean;
  churchesCount?: number;
  createdAt: string;
}

export interface CreateUnionRequest {
  name: string;
  description?: string;
  address: string;
  city: string;
  state: string;
  country: string;
  phone?: string;
  email?: string;
  website?: string;
  logoUrl?: string;
}