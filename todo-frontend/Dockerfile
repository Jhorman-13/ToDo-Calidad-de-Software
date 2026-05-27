# Etapa 1: Construcción (Build)

FROM node:22-alpine AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de dependencias
COPY package.json package-lock.json ./

# Instalar las dependencias
RUN npm install

# Copiar el resto del código del proyecto
COPY .. .

# Compilar la aplicación para producción (esto crea la carpeta /dist)
RUN npm run build


# Etapa 2: Servidor Web (Nginx)

FROM nginx:alpine

# Copiar los archivos compilados de la etapa anterior a la carpeta pública de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Exponer el puerto 80 (el puerto por defecto de Nginx)
EXPOSE 80

# Iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]