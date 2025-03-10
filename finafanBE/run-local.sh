set -e

echo "Starting application in local environment..."
cp .env.local .env
docker compose down -v
docker compose up --build -d

echo "Application started successfully in local environment!"
echo "Spring App: http://localhost:8080"
echo "Kibana: http://localhost:5601"
echo "Prometheus: http://localhost:9090"
echo "Grafana: http://localhost:3000"

