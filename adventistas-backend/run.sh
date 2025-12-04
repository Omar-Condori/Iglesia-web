#!/bin/bash

# Script de compilación correcta para el proyecto
echo "Limpiando proyecto..."
rm -rf target
rm -rf ~/.m2/repository/com/iglesia

echo "Compilando con Maven..."
./mvnw clean install -DskipTests -U

echo "Verificando clases generadas..."
if [ -f "target/classes/com/iglesia/adventistas/modules/news/mapper/NewsMapperImpl.class" ]; then
    echo "✓ NewsMapperImpl.class generado"
else
    echo "✗ NewsMapperImpl.class NO generado"
    exit 1
fi

echo "Verificando interfaz..."
if javap target/classes/com/iglesia/adventistas/modules/news/mapper/NewsMapperImpl.class | grep -q "implements"; then
    echo "✓ NewsMapperImpl implementa la interfaz correctamente"
else
    echo "✗ NewsMapperImpl NO implementa la interfaz"
    echo "Intentando recompilar las clases generadas..."
    ./mvnw compiler:compile
fi

echo "Iniciando aplicación..."
./mvnw spring-boot:run -DskipTests
