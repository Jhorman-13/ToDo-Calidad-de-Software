const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    video: true,
    trashAssetsBeforeRuns: true,
    baseUrl: 'http://localhost:3001', // <-- Asegúrate de que sea el puerto de tu login front
    allowCypressEnv: false,           // <-- Apaga la advertencia de seguridad que sale en el log
    chromeWebSecurity: false,         // <-- Evita que Electron bloquee peticiones entre el front y el back
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});