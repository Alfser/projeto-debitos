ARG NODE_VERSION=22.14.0-alpine
ARG NGINX_VERSION=alpine3.21

# Stage 1: Build the Angular app
FROM node:${NODE_VERSION} AS builder

WORKDIR /app

# Copy package files first for better caching
COPY package.json package-lock.json ./

# Use cache mount for npm dependencies
RUN --mount=type=cache,target=/root/.npm \
    npm ci

# Copy all source files
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve the app with Nginx
FROM nginxinc/nginx-unprivileged:${NGINX_VERSION} AS runner

# Copy custom nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built assets with proper permissions
COPY --chown=nginx:nginx --from=builder /app/dist/*/browser /usr/share/nginx/html

# Expose the port Nginx listens on
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:8080 || exit 1

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]