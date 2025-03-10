set -e

echo "Starting application in dev environment..."
cp .env.dev .env
docker compose down
docker compose up --build -d

echo "Application started successfully in dev environment!"
echo "Spring App: http://localhost:8080"
echo "Kibana: http://localhost:5601"
echo "Prometheus: http://localhost:9090"
echo "Grafana: http://localhost:3000"