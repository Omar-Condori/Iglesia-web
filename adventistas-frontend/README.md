# Adventistas.org - Frontend

Frontend desarrollado con Angular 17 para el portal de la Iglesia Adventista.

## 🚀 Tecnologías

- Angular 17
- TypeScript
- Tailwind CSS
- Angular Material
- NGX Translate (i18n)
- SweetAlert2
- FontAwesome
- JWT Decode

## 📋 Requisitos Previos

- Node.js 18+ 
- npm 9+
- Angular CLI 17

## 🔧 Instalación

1. **Clonar el repositorio**
```bash
cd adventistas-frontend
```

2. **Instalar dependencias**
```bash
npm install
```

3. **Configurar variables de entorno**

Editar `src/environments/environment.ts`:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  mediaUrl: 'http://localhost:8080',
  appName: 'Adventistas.org',
  version: '1.0.0'
};
```

4. **Ejecutar en modo desarrollo**
```bash
ng serve
```

La aplicación estará disponible en `http://localhost:4200`

## 🏗️ Compilar para Producción
```bash
ng build --configuration production
```

Los archivos compilados estarán en `dist/adventistas-frontend`

## 📦 Estructura del Proyecto
```
src/
├── app/
│   ├── core/              # Servicios, guards, interceptors, modelos
│   ├── shared/            # Componentes, pipes, directivas compartidas
│   ├── modules/           # Módulos de la aplicación
│   │   ├── public/        # Módulos públicos
│   │   └── admin/         # Módulos de administración
│   ├── layouts/           # Layouts (public, admin)
│   └── app.module.ts
├── assets/                # Recursos estáticos
│   ├── i18n/             # Archivos de traducción
│   └── images/           # Imágenes
├── environments/          # Configuración de entornos
└── styles.scss           # Estilos globales
```

## 🔑 Credenciales de Prueba

**Administrador:**
- Email: admin@adventistas.org
- Password: Admin123!

## 🌐 Funcionalidades

### Públicas
- ✅ Página de inicio
- ✅ Listado de noticias con filtros
- ✅ Detalle de noticia
- ✅ Buscador de iglesias
- ✅ Catálogo de cursos
- ✅ Descargas
- ✅ Videos
- ✅ Internacionalización (ES/PT)

### Panel Administrativo
- ✅ Dashboard con estadísticas
- ✅ CRUD de Noticias
- ✅ CRUD de Iglesias
- ✅ CRUD de Cursos y Lecciones
- ✅ CRUD de Categorías
- ✅ CRUD de Departamentos
- ✅ CRUD de Descargas
- ✅ CRUD de Videos
- ✅ CRUD de Usuarios
- ✅ Gestión de roles y permisos
- ✅ Subida de archivos multimedia

## 🛠️ Scripts Disponibles
```bash
# Desarrollo
npm start                    # ng serve

# Compilación
npm run build               # ng build
npm run build:prod          # ng build --configuration production

# Tests
npm test                    # ng test
npm run test:coverage       # ng test --code-coverage

# Linting
npm run lint                # ng lint

# Análisis de bundle
npm run analyze             # ng build --stats-json && webpack-bundle-analyzer
```

## 🔒 Seguridad

- Autenticación JWT
- Guards de ruta
- Interceptores HTTP
- Validación de formularios
- Sanitización de HTML
- CORS configurado

## 📱 Responsive

- Mobile First
- Breakpoints: sm (640px), md (768px), lg (1024px), xl (1280px)
- Menú hamburguesa en móviles

## 🎨 Temas y Estilos

- Tailwind CSS con configuración personalizada
- Colores corporativos
- Componentes reutilizables
- Animaciones suaves

## 🐛 Solución de Problemas

**Puerto ya en uso:**
```bash
ng serve --port 4201
```

**Error de proxy:**
Verificar que el backend esté corriendo en `http://localhost:8080`

**Errores de compilación:**
```bash
rm -rf node_modules package-lock.json
npm install
```

## 📄 Licencia

Copyright © 2024 Adventistas.org