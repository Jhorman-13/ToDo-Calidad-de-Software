package org.example.auth.steps;

import io.cucumber.java.en.*;
import org.example.auth.service.AuthService;

import static org.junit.jupiter.api.Assertions.*;

public class AccessSteps {

    private AuthService service = new AuthService();
    private String response;

    @Given("Las credenciales del usuario son válidas")
    public void validCredentials() {
        service.register("thomas", "1234");
    }

    @When("El sistema procesa la solicitud de login")
    public void LoginProcess() {
        response = service.login("thomas", "1234");
    }

    @Then("El código de respuesta debe ser {int}")
    public void responseCode(Integer code) {
        assertEquals(200, code);
    }

    @And("El mensaje debe ser {string}")
    public void responseMessage(String message) {
        assertEquals(message, response);
    }
}
