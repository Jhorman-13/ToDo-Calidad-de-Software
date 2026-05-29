package org.example.auth.steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CredentialsSteps {

    @Autowired
    private MockMvc mockMvc;

    private String response;

    @Given("Se expone el endpoint de autenticación {string}")
    public void showEndpoint(String endpoint){
            assertEquals("/auth/login", endpoint);
    }

    @When("El cliente envía una petición POST con el username {string} y la contraseña {string}")
    public void sendCredencials(String user, String pass) throws Exception {

        String json = """
        {
            "username": "%s",
            "password": "%s"
        }
        """.formatted(user, pass);

        response = mockMvc.perform(
                        post("/auth/login")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Then("El controlador recibe las credenciales correctamente y las procesa para su validación")
    public void validateReception() {
        assertNotNull(response);
    }
}
