Feature: crear una nueva tarea

  Scenario: Crear una nueva tarea mediante el sistema dando correctamente la información solicitada
    Given que el usuario ha iniciado el sistema y está en la sección del menú crear tarea
    When Se ingresa la descripción "Tarea de calculo", la fecha "2026-04-08" y la prioridad "Media"
    Then el sistema debe mostrar un mensaje confirmando que la tarea fue creada
    And la tarea "Tarea de calculo" debe aparecer en el listado de tareas pendientes
      # --- Criterio 6: Descripción vacia ---

  Scenario: Crear una tarea con una descripción
    Given que el usuario ha iniciado el sistema y está en la sección del menú crear tarea
    When Se ingresa la descripción "a", la fecha "2026-04-08" y la prioridad "Media"
    Then el sistema debe confirmar que la tarea fue creada exitosamente

    Scenario: Crear una nueva tarea sin ingresar la descripción
      Given que el usuario ha iniciado el sistema y está en la sección del menú crear tarea
      When Se ingresa la descripción "", la fecha "2026-04-08" y la prioridad "Media"
      Then el sistema debe mostrar un mensaje de error indicando que la descripción es obligatoria

    Scenario: Crear una nueva tarea sin ingresar la fecha
        Given que el usuario ha iniciado el sistema y está en la sección del menú crear tarea
        When Se ingresa la descripción "Tarea de calculo" y la prioridad "Media" sin ingresar la fecha
        Then el sistema debe mostrar un mensaje de error indicando que la fecha es obligatoria

    Scenario: Crear una nueva tarea sin ingresar la prioridad
        Given que el usuario ha iniciado el sistema y está en la sección del menú crear tarea
        When Se ingresa la descripción "Tarea de calculo" y la fecha "2026-04-08" sin ingresar la prioridad
        Then el sistema debe mostrar un mensaje de error indicando que la prioridad es obligatoria

      # --- Criterio 3: ID Único ---
  Scenario: Asignación automática de un ID único a cada tarea nueva
    Given que el usuario está en el sistema de gestión de tareas
    When crea una tarea con descripción "Ecuaciones diferenciales" y prioridad "alta"
    And crea otra tarea con descripción "Estadistica discreta" y prioridad "media"
    Then el sistema debe haber asignado un ID a la primera tarea
    And el sistema debe haber asignado un ID a la segunda tarea
    And los IDs generados para ambas tareas deben ser diferentes

  # --- Criterio 4: Estado Pendiente ---
  Scenario: Almacenamiento de una nueva tarea con estado inicial pendiente
    Given que el usuario se encuentra en el sistema de gestión de tareas
    When el usuario crea una nueva tarea con descripción "Algebra lineal" y prioridad "alta"
    Then la tarea debe guardarse exitosamente en el sistema
    And el estado de la tarea recién creada debe ser exactamente "pendiente"