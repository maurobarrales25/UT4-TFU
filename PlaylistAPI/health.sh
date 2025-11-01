# Probar Health Endpoint Monitoring
echo " Probando Health Endpoint"
echo ""

echo " Probando endpoint normal."
sleep 2
curl -X GET http://localhost:8020/playlistapi/playlist/health
echo ""

# Detener DB
docker stop mongodb
echo ""

# Probar endpoint con DB caida.
echo " Probando endpoint con DB caida."
sleep 2
curl -X GET http://localhost:8020/playlistapi/playlist/health
echo ""

# Reanudar DB
docker start mongodb
echo ""

echo "Fin de la prueba del Health Endpoint. Presiona ENTER para cerrar."
read
