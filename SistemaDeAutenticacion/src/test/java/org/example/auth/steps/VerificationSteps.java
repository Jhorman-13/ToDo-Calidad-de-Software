package org.example.auth.steps;

import io.cucumber.java.en.*;
import org.example.auth.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationSteps {
    private AuthService service = new AuthService();
    private String username;
    private String password;
    private String response;

    @Given("El sistema recibió una petición de login con credenciales")
    public void credentialsReceived() {
        service.register("thomas", "1234");
    }

    @When("El servicio de autenticación busca el username {string} y la contraseña {string} en la base de datos")
    public void credentialVerification(String user, String pass){
        this.username = user;
        this.password = pass;

        response = service.login(username, password);

    }

    @Then("El resultado de la validación de la contraseña debe ser {string}")
    public void answer(String expected) {
        boolean result = response.equals("Login exitoso");
        assertEquals(Boolean.parseBoolean(expected), result);
    }
}
