package com.ortiz.Proyecto.models;

/**
 * DTO para registrar un nuevo usuario del sistema.
 * Todos los campos son requeridos.
 */
public record DatosRegistroUsuario(
        String nombre,
        String usuario,
        String contraseña,
        Rol rol) {
}
