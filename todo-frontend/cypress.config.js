const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: 'http://localhost:3001', // <-- Verifica que este puerto sea el correcto de tu contenedor frontend
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});