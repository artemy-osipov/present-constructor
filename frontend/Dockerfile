FROM nginx:1.13.6-alpine

COPY nginx/default.conf /etc/nginx/conf.d/
COPY dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
