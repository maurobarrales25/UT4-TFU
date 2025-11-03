#!/bin/sh
set -e

host=${KAFKA_BOOTSTRAP_SERVERS:-kafka-broker:9092}

echo "Esperando a que Kafka esté disponible en $host..."
until nc -z $(echo $host | cut -d: -f1) $(echo $host | cut -d: -f2); do
  sleep 2
done

echo "Kafka disponible, iniciando aplicación..."
exec "$@"
