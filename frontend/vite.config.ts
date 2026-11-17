import { defineConfig } from 'vite';

// Las peticiones a /api se redirigen al backend Java (Javalin en el puerto 7070).
export default defineConfig({
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:7070',
    },
  },
});
