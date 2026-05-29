Feature: Sistema de Autenticación

  Scenario: Ingreso de credenciales
    Given Se expone el endpoint de autenticación "/auth/login"
    When El cliente envía una petición POST con el username "thomas" y la contraseña "1234"
    Then El controlador recibe las credenciales correctamente y las procesa para su validación

  Scenario: Verificación de credenciales
    Given El sistema recibió una petición de login con credenciales
    When El servicio de autenticación busca el username "thomas" y la contraseña "1234" en la base de datos
    Then El resultado de la validación de la contraseña debe ser "true"

  Scenario: Acceso exitoso al sistema
    Given Las credenciales del usuario son válidas
    When El sistema procesa la solicitud de login
    Then El código de respuesta debe ser 200
    And El mensaje debe ser "Login exitoso"

  Scenario: Incremento de intentos fallidos por credenciales incorrectas
    Given Existe un usuario registrado con username "thomas"
    When El cliente envía una contraseña incorrecta
    Then El sistema rechaza el acceso
    And El sistema incrementa el contador de intentos fallidos

