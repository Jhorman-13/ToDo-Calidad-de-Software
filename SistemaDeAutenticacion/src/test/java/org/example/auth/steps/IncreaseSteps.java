package org.example.auth.steps;

import io.cucumber.java.en.*;
import org.example.auth.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

public class IncreaseSteps {

    private AuthService service = new AuthService();
    private String response;
    private String username;
    private int failedAttempts = 0;

    @Given("Existe un usuario registrado con username {string}")
    public void userRegistered(String username) {
        service.register(username, "1234");
        this.username = username;
    }

    @When("El cliente envía una contraseña incorrecta")
    public void incorrectPassword() {
        response = service.login(username, "1235");
        failedAttempts++;
    }

    @Then("El sistema rechaza el acceso")
    public void accessRejected() {
        assertEquals("Credenciales incorrectas", response);
    }

    @And("El sistema incrementa el contador de intentos fallidos")
    public void counterIncremented() {
        assertEquals(1, failedAttempts);
    }
}
