package com.ortiz.Proyecto.models;

/**
 * DTO recibido en POST /api/auth/login
 */
public record DatosLogin(String usuario, String contraseña) {
}
