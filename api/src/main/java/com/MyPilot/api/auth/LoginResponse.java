package com.MyPilot.api.auth;

public record LoginResponse(String token, Long id, String nombre, String apellido, String rol) {
}
