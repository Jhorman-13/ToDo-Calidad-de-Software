package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Ruta donde están tus archivos .feature
        features = "src/test/resources/features",

        // Paquete donde están tus clases Java con los @Given, @When, @Then
        glue = "org/example/Steps",

        // Formato de los reportes que se generarán al terminar las pruebas
        plugin = {"pretty", "html:target/cucumber-reports.html"},

        // Genera sugerencias de código en formato camelCase si te falta algún Step
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunCucumberTest {
    // Esta clase se deja vacía por dentro.
    // Toda la magia ocurre en las anotaciones de arriba.
}