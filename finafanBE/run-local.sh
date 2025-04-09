#!/bin/bash
set -e

echo "Creating necessary configuration directories..."
mkdir -p config/prometheus config/grafana/provisioning/datasources config/grafana/provisioning/dashboards config/logstash/pipeline config/fluentd/conf logs/app1 logs/app2 logs/app3 config/nginx

echo "Generating Prometheus configuration..."
cat > config/prometheus/prometheus.yml << EOF
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'spring-apps'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['spring-app1:8081', 'spring-app2:8082', 'spring-app3:8083']

  - job_name: 'redis'
    static_configs:
      - targets: ['redis-server:6379', 'redis-slave-1:6379', 'redis-slave-2:6379']
EOF

echo "Generating Grafana datasource configuration..."
cat > config/grafana/provisioning/datasources/prometheus.yml << EOF
apiVersion: 1

datasources:
  - name: Prometheus
    type: prometheus
    access: proxy
    url: http://prometheus:9090
    isDefault: true
EOF

echo "Generating Logstash configuration..."
cat > config/logstash/pipeline/logstash.conf << EOF
input {
  beats {
    port => 5044
  }
  tcp {
    port => 5000
  }
}

filter {
  if [message] =~ /^\{.*\}$/ {
    json {
      source => "message"
    }
  }
}

output {
  elasticsearch {
    hosts => ["elasticsearch:9200"]
    index => "finafan-%{+YYYY.MM.dd}"
  }
}
EOF

echo "Generating Fluentd configuration..."
cat > config/fluentd/conf/fluent.conf << EOF
<source>
  @type forward
  port 24224
  bind 0.0.0.0
</source>

<match *.**>
  @type copy
  <store>
    @type elasticsearch
    host elasticsearch
    port 9200
    logstash_format true
    logstash_prefix fluentd
    logstash_dateformat %Y%m%d
    include_tag_key true
    type_name access_log
    tag_key @log_name
    flush_interval 1s
  </store>
  <store>
    @type stdout
  </store>
</match>
EOF

echo "Generating NGINX configuration for load balancing..."
cat > config/nginx/nginx.conf << EOF
upstream spring_backend {
    server spring-app1:8081;
    server spring-app2:8082;
    server spring-app3:8083;
}

server {
    listen 80;
    
    location /api {
        proxy_pass http://spring_backend;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
    
    location /actuator {
        proxy_pass http://spring_backend;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
    }
}
EOF

echo "Starting application in local environment..."
docker compose down -v
docker compose up --build -d

echo "Application started successfully in local environment!"
echo "Spring Apps: http://localhost:8081, http://localhost:8082, http://localhost:8083"
echo "NGINX Load Balancer: http://localhost:80"
echo "Kibana: http://localhost:5601"
echo "Prometheus: http://localhost:9090"
echo "Grafana: http://localhost:3000"
echo "nGrinder Controller: http://localhost:8080"