package org.example.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper; //convierte objetos Java a JSON / Convert Java objects to JSON
import org.example.auth.dto.LoginRequest;
import org.example.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; //inyecta dependencias automáticamente / automatically injects dependencies
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginRequest request = new LoginRequest("thomas", "1234");

        when(authService.login("thomas", "1234"))
                .thenReturn("Login exitoso");

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login exitoso"));
    }

    @Test
    void shouldRegisterUser() throws Exception {

        when(authService.register("thomas", "1234"))
                .thenReturn("Usuario registrado correctamente.");

        String json = """
        {
            "username": "thomas",
            "password": "1234"
        }
        """;

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado correctamente."));
    }

    @Test
    void shouldRecoverPassword() throws Exception {

        when(authService.recover("thomas"))
                .thenReturn("Contraseña restablecida");

        mockMvc.perform(post("/auth/recover")
                        .param("username", "thomas"))
                .andExpect(status().isOk())
                .andExpect(content().string("Contraseña restablecida"));
    }

}
