package org.example.auth.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private final AuthService service = new AuthService();

    @Test
    void shouldRegisterUser() {
        String result = service.register("thomas", "1234");
        assertEquals("Usuario registrado correctamente.", result);
    }

    @Test
    void shouldNotRegisterExistingUser() {
        service.register("thomas", "1234");
        String result = service.register("thomas", "1234");
        assertEquals("Usuario ya existe.", result);
    }

    @Test
    void shouldLoginCorrectly() {
        service.register("thomas", "1234");
        String result = service.login("thomas", "1234");
        assertEquals("Login exitoso", result);
    }

    @Test
    void shouldBlockUserAfterThreeFailedAttempts() {
        service.register("thomas", "1234");
        service.login("thomas", "wrong1");
        service.login("thomas", "wrong2");
        String result = service.login("thomas", "wrong3");
        assertEquals("Credenciales incorrectas", result);
    }

    @Test
    void shouldNotLoginBlockedUser() {
        service.register("thomas", "1234");
        service.login("thomas", "wrong1");
        service.login("thomas", "wrong2");
        service.login("thomas", "wrong3");
        String result = service.login("thomas", "1234");
        assertEquals("Usuario bloqueado", result);
    }

    @Test
    void ShouldNotLoginNonExistingUser() {
        String result = service.login("Thoma", "1234");
        assertEquals("Usuario no encontrado", result);
    }

    @Test
    void ShouldRecoverPassword() {
        service.register("thomas", "1234");
        String result = service.recover("thomas");
        assertEquals("Contraseña restablecida", result);
    }



}
