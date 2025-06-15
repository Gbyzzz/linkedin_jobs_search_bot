FROM node:alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install && npm install -g gzipper
ARG ENV
COPY . ./
COPY src/environments/environment.$ENV.ts ./src/environments/environment.ts
RUN npm run build

FROM nginx:alpine
ARG ENV
COPY --from=builder /app/dist/linkedin-frontend/browser/ /usr/share/nginx/html/
COPY /nginx-$ENV.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]
