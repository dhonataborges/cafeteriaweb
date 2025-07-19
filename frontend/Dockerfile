# Etapa 1: Build da aplicação Angular
FROM node:20.19-alpine AS builder

WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps

COPY . .
RUN npm run build --configuration=production --project=cafeteriaweb

# Etapa 2: Servir com NGINX
FROM nginx:stable

# Remove config default e adiciona a personalizada
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia o build Angular
COPY --from=builder /app/dist/cafeteriaweb /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]