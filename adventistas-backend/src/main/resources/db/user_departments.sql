-- ==========================================
-- TABLA: user_departments
-- Relación usuarios con departamentos y permisos
-- ==========================================

CREATE TABLE IF NOT EXISTS user_departments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    department_id BIGINT NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
    can_view BOOLEAN DEFAULT true,
    can_edit BOOLEAN DEFAULT false,
    can_publish BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, department_id)
);

CREATE INDEX idx_user_departments_user ON user_departments(user_id);
CREATE INDEX idx_user_departments_dept ON user_departments(department_id);

-- ==========================================
-- PERMISOS POR DEPARTAMENTO
-- Generar permisos dinámicos basados en departamentos existentes
-- ==========================================

-- Permisos para Ministerio de la Familia (slug: familia)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de la Familia', 'departments.familia.view', 'Ver contenido del Ministerio de la Familia', 'departments', NOW(), NOW()),
('Editar Ministerio de la Familia', 'departments.familia.edit', 'Editar contenido del Ministerio de la Familia', 'departments', NOW(), NOW()),
('Publicar Ministerio de la Familia', 'departments.familia.publish', 'Publicar contenido del Ministerio de la Familia', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio Joven (slug: jovenes)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio Joven', 'departments.jovenes.view', 'Ver contenido del Ministerio Joven', 'departments', NOW(), NOW()),
('Editar Ministerio Joven', 'departments.jovenes.edit', 'Editar contenido del Ministerio Joven', 'departments', NOW(), NOW()),
('Publicar Ministerio Joven', 'departments.jovenes.publish', 'Publicar contenido del Ministerio Joven', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Escuela Sabática (slug: escuela-sabatica)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Escuela Sabática', 'departments.escuela-sabatica.view', 'Ver contenido de Escuela Sabática', 'departments', NOW(), NOW()),
('Editar Escuela Sabática', 'departments.escuela-sabatica.edit', 'Editar contenido de Escuela Sabática', 'departments', NOW(), NOW()),
('Publicar Escuela Sabática', 'departments.escuela-sabatica.publish', 'Publicar contenido de Escuela Sabática', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Educación (slug: educacion)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Educación', 'departments.educacion.view', 'Ver contenido de Educación', 'departments', NOW(), NOW()),
('Editar Educación', 'departments.educacion.edit', 'Editar contenido de Educación', 'departments', NOW(), NOW()),
('Publicar Educación', 'departments.educacion.publish', 'Publicar contenido de Educación', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio Infantil (slug: infantil)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio Infantil', 'departments.infantil.view', 'Ver contenido del Ministerio Infantil', 'departments', NOW(), NOW()),
('Editar Ministerio Infantil', 'departments.infantil.edit', 'Editar contenido del Ministerio Infantil', 'departments', NOW(), NOW()),
('Publicar Ministerio Infantil', 'departments.infantil.publish', 'Publicar contenido del Ministerio Infantil', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio de la Mujer (slug: mujer)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de la Mujer', 'departments.mujer.view', 'Ver contenido del Ministerio de la Mujer', 'departments', NOW(), NOW()),
('Editar Ministerio de la Mujer', 'departments.mujer.edit', 'Editar contenido del Ministerio de la Mujer', 'departments', NOW(), NOW()),
('Publicar Ministerio de la Mujer', 'departments.mujer.publish', 'Publicar contenido del Ministerio de la Mujer', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio de Mayordomía (slug: mayordomia)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de Mayordomía', 'departments.mayordomia.view', 'Ver contenido del Ministerio de Mayordomía', 'departments', NOW(), NOW()),
('Editar Ministerio de Mayordomía', 'departments.mayordomia.edit', 'Editar contenido del Ministerio de Mayordomía', 'departments', NOW(), NOW()),
('Publicar Ministerio de Mayordomía', 'departments.mayordomia.publish', 'Publicar contenido del Ministerio de Mayordomía', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio de Publicaciones (slug: publicaciones)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de Publicaciones', 'departments.publicaciones.view', 'Ver contenido del Ministerio de Publicaciones', 'departments', NOW(), NOW()),
('Editar Ministerio de Publicaciones', 'departments.publicaciones.edit', 'Editar contenido del Ministerio de Publicaciones', 'departments', NOW(), NOW()),
('Publicar Ministerio de Publicaciones', 'departments.publicaciones.publish', 'Publicar contenido del Ministerio de Publicaciones', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio de Salud (slug: salud)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de Salud', 'departments.salud.view', 'Ver contenido del Ministerio de Salud', 'departments', NOW(), NOW()),
('Editar Ministerio de Salud', 'departments.salud.edit', 'Editar contenido del Ministerio de Salud', 'departments', NOW(), NOW()),
('Publicar Ministerio de Salud', 'departments.salud.publish', 'Publicar contenido del Ministerio de Salud', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permisos para Ministerio de ASA (slug: asa)
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Ver Ministerio de ASA', 'departments.asa.view', 'Ver contenido del Ministerio de ASA', 'departments', NOW(), NOW()),
('Editar Ministerio de ASA', 'departments.asa.edit', 'Editar contenido del Ministerio de ASA', 'departments', NOW(), NOW()),
('Publicar Ministerio de ASA', 'departments.asa.publish', 'Publicar contenido del Ministerio de ASA', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Permiso global para gestionar todos los departamentos
INSERT INTO permissions (name, slug, description, module, created_at, updated_at) VALUES
('Gestionar Departamentos', 'departments.manage', 'Gestionar todos los departamentos', 'departments', NOW(), NOW())
ON CONFLICT (slug) DO NOTHING;

-- Verificar permisos creados
SELECT COUNT(*) as total_permissions FROM permissions WHERE module = 'departments';
