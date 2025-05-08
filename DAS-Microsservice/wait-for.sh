#!/bin/sh
# wait-for.sh

set -e

host="$1"
shift
cmd="$@"

until nc -z "$host" 5672; do
  echo "Aguardando o RabbitMQ subir, porta $host:5672..."
  sleep 3
done

echo "RabbitMQ subiu - executando o microservice..."
exec $cmd
