#!/bin/bash
set -euo pipefail

VALET_KEY_URL="http://localhost:8089"
USER_API_URL="http://localhost:8000/usersapi/api/users"

get_token() {
  local scope="$1" # parametro del scope puede ser 'read' o 'write'
  echo "Obteniendo token con scope=${scope}..."
  
  # Extrae el token usando grep y cut
  token=$(curl -s "${VALET_KEY_URL}/?scope=${scope}" | grep -o '"token" *: *"[^"]*"' | cut -d'"' -f4)
  
  if [ -z "$token" ]; then
    echo "No se pudo obtener token para scope=${scope}"
    exit 1
  fi
  echo "Token obtenido: $token"
}

# Prueba sin token
echo ""
echo "Intentando GET sin token (debería fallar con 401 o 403)..."
curl -s -w "\nHTTP_CODE:%{http_code}\n" \
  "${USER_API_URL}/1"

# Prueba con scope=read (GET)
echo ""
get_token "read"
echo "Haciendo GET con token (scope=read)..."
curl -s -w "\nHTTP_CODE:%{http_code}\n" \
  -H "Authorization: Bearer ${token}" \
  "${USER_API_URL}/1"

# Prueba con scope=write (POST), si ya se ejecutó antes, puede fallar por duplicado
echo ""
get_token "write"
echo "Haciendo POST con token (scope=write)..."
curl -s -w "\nHTTP_CODE:%{http_code}\n" \
  -H "Authorization: Bearer ${token}" \
  -H "Content-Type: application/json" \
  -d '{"name": "Nuevo", "email": "nuevo@example.com"}' \
  -X POST "${USER_API_URL}"
