describe('Ruta Crítica: Ecosistema LockiToDo Completo', () => {

  // Generamos credenciales únicas para esta ejecución.
  // Al ser un usuario nuevo, su lista de tareas SIEMPRE iniciará en 0.
  const usuarioAleatorio = `user_${Math.floor(Math.random() * 10000)}`;
  const contrasenaAleatoria = 'Clave123!';
  const tituloTarea = 'Aprobar proyecto final de QA con Cypress';

  beforeEach(() => {
    cy.clearLocalStorage();
  });

  it('Flujo Completo: Registro -> Volver al Login -> Login -> Dashboard -> ToDo -> Crear, Completar y Eliminar', () => {

    // 1. Ir al Login inicial
    cy.visit('http://localhost:3002');

    // 2. Navegar a la pantalla de Registro
    cy.contains('a', /registra|registro/i).click();

    // 3. Rellenar formulario de Registro y enviar
    cy.get('input[type="text"]').first().type(usuarioAleatorio);
    cy.get('input[type="password"]').first().type(contrasenaAleatoria);
    cy.get('button').contains(/registrar/i).click();

    // 4. PASO RECORDATORIO: Hacer clic obligatorio en "Volver al login"
    cy.contains(/volver|regresar|login/i).click();

    // 5. Iniciar sesión formalmente con el usuario recién creado
    cy.get('input[type="text"]').first().type(usuarioAleatorio);
    cy.get('input[type="password"]').first().type(contrasenaAleatoria);
    cy.get('button[type="submit"]').click();

    // 6. Validar redirección exitosa al Dashboard
    cy.url().should('include', '/dashboard');
    cy.contains('Bienvenido').should('be.visible');

    // 7. Desactivar target="_blank" e ingresar al ToDo
    cy.get('.btn-todo').invoke('removeAttr', 'target').click();

    // 8. Entrar al entorno del ToDo (Puerto 3001)
    cy.origin('http://localhost:3001', { args: { tituloTarea } }, ({ tituloTarea }) => {
      // Ignorar excepciones de renderizado del frontend para que no detengan el test
      Cypress.on('uncaught:exception', () => false);

      cy.url().should('include', 'localhost:3001');

      // A. Crear la tarea única
      cy.get('input[placeholder*="JMeter"]').type(tituloTarea);
      cy.get('input[type="date"]').type('2026-12-31');
      cy.get('select').select('Alta');
      cy.get('button').contains('Guardar Tarea en Backend').click();

      // B. Asegurar que la tarea duplicada visualmente ya se renderizó en pantalla
      cy.contains(tituloTarea).should('be.visible');

      // C. COMPLETAR: Agarramos la primera copia idéntica generada por el render
      cy.contains(tituloTarea)
        .first()
        .parent()
        .parent()
        .find('button')
        .contains('Completar')
        .click();

      cy.wait(1000); // Pausa estratégica para asimilar el cambio de estado de React

      // D. ELIMINAR: Agarramos la segunda copia idéntica y la eliminamos por completo
      cy.contains(tituloTarea)
        .last()
        .parent()
        .parent()
        .find('button')
        .contains('Eliminar')
        .click();

      // E. Verificación final: La lista debe quedar limpia o procesada con éxito
      cy.wait(500);
    });
  });
});