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
docker start mongodb
echo ""

# Probar endpoint con microservicio caido.
echo " Probando endpoint con UserAPI caida."
sleep 2
docker stop user-api
curl -X GET http://localhost:8020/playlistapi/playlist/health
echo ""
docker start user-api

# Probar endpoint con microservicio con DB caida.
echo " Probando endpoint con UserAPI con DB caida."
sleep 2
docker stop user-db
curl -X GET http://localhost:8020/playlistapi/playlist/health
echo ""
docker start user-db

echo "Fin de la prueba del Health Endpoint. Presiona ENTER para cerrar."
read
