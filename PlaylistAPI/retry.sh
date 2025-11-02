# Probar Retry cuando falla un servicio externo
echo " Prueba de Retry"
echo ""

docker stop user-api

echo ""

echo " Probando endpoint que dispara retry."
curl -X GET http://localhost:8020/playlistapi/playlist/user/1 &
echo ""

sleep 5

#Reanudar UserAPI antes del ultimo intento.
echo " Reanudando UserAPI antes del ultimo intento."

docker start user-api

echo ""

sleep 2

echo ""
echo " Ver ultimos 5 logs de playlist-api."
echo ""
docker logs --tail 7 playlist-api
echo ""

echo "Fin de la prueba. Presiona ENTER para cerrar."
read
