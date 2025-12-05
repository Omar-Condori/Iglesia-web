# Guía de Instalación y Solución de Problemas

Este documento detalla los pasos para configurar el entorno de desarrollo correctamente, asegurando la compatibilidad con Angular 17.

## Requisitos Previos

- **Sistema Operativo**: macOS
- **Node.js**: v20 LTS (Iron)
- **npm**: v10+

## Paso 1: Instalar Node.js v20 LTS

Si tienes una versión diferente de Node (como la v24), es necesario cambiarla. Recomendamos usar `nvm` (Node Version Manager).

1.  **Instalar nvm** (si no lo tienes):
    ```bash
    curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
    # Reinicia tu terminal después de instalar
    ```

2.  **Instalar y usar Node 20**:
    ```bash
    nvm install 20
    nvm use 20
    nvm alias default 20
    ```

3.  **Verificar versión**:
    ```bash
    node -v
    # Debería mostrar v20.x.x
    ```

## Paso 2: Limpiar Instalación Anterior

Es CRÍTICO eliminar los artefactos de instalaciones fallidas anteriores para evitar conflictos.

Ejecuta estos comandos en la raíz del proyecto:

```bash
# Eliminar carpeta de dependencias
rm -rf node_modules

# Eliminar archivo de bloqueo (se regenerará)
rm package-lock.json

# Limpiar caché de npm (opcional pero recomendado si hubo errores graves)
npm cache clean --force
```

## Paso 3: Instalar Dependencias

Hemos configurado un archivo `.npmrc` para manejar problemas de red y dependencias heredadas automáticamente.

1.  **Ejecutar instalación**:
    ```bash
    npm install
    ```
    *Nota: Gracias al archivo `.npmrc`, no necesitas agregar banderas extra como `--legacy-peer-deps`, ya están configuradas.*

## Paso 4: Ejecutar el Proyecto

Una vez instaladas las dependencias, puedes iniciar el servidor de desarrollo.

1.  **Iniciar servidor**:
    ```bash
    npm start
    ```

2.  **Abrir en navegador**:
    Visita `http://localhost:4200`

## Solución de Problemas Comunes

- **Error ERESOLVE**: Si ves errores de resolución de dependencias, asegúrate de que el archivo `.npmrc` existe en la raíz del proyecto.
- **Timeouts**: Si la instalación falla por tiempo de espera, el archivo `.npmrc` ya ha aumentado el timeout a 10 minutos. Verifica tu conexión a internet.
- **Angular Material**: Este proyecto ha sido configurado para NO usar Angular Material. Si ves errores relacionados con `mat-` o `@angular/material`, busca en el código y elimina esas referencias, ya que la librería ha sido removida del `package.json`.
