const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: 'http://localhost:3001',
    allowCypressEnv: false,
    chromeWebSecurity: false,
    video: true,                      // <-- ¡Aquí está la magia para el video!
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});