package com.iglesia.adventistas.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "Admin123!";

        // Generar varios hashes para comparar
        System.out.println("Generando hashes BCrypt para: " + password);
        System.out.println("===============================================");

        // Hash actual en BD
        String storedHash = "$2a$10$N9qo8uLOickgx2ZMRZoMye7I62xN.yvYjPCyPQXNVN1gZJhGLKjKS";
        System.out.println("Hash original en BD: " + storedHash);
        System.out.println("¿Coincide con 'Admin123!'? " + encoder.matches(password, storedHash));
        System.out.println();

        // Hash que intentamos usar
        String newHash = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cyhXdgKDK.VfQf4V9/3vJQU8.nWvi";
        System.out.println("Hash actualizado: " + newHash);
        System.out.println("¿Coincide con 'Admin123!'? " + encoder.matches(password, newHash));
        System.out.println();

        // Generar hash fresco
        String freshHash = encoder.encode(password);
        System.out.println("Hash fresco generado: " + freshHash);
        System.out.println("¿Coincide con 'Admin123!'? " + encoder.matches(password, freshHash));
        System.out.println();

        System.out.println("===============================================");
        System.out.println("SQL para actualizar:");
        System.out.println("UPDATE users SET password = '" + freshHash + "' WHERE email = 'admin@adventistas.org';");
    }
}
