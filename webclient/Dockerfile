# Stage 1: Build the Angular application
FROM node:20.18.0-alpine AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration production

# Stage 2: Serve the Angular app with Nginx
FROM nginx:alpine

COPY --from=build /app/dist/webclient /usr/share/nginx/html

# Copy the nginx.conf file
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]