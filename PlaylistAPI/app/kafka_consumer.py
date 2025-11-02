from confluent_kafka import DeserializingConsumer
from confluent_kafka.serialization import StringDeserializer, Deserializer
from confluent_kafka.admin import AdminClient
from controller.playlist_controller import playlist_service
import asyncio

import json
import time
import threading

class JSONDeserializerClass(Deserializer):
    def __init__(self):
        super().__init__()

    def __call__(self, data, ctx):
        if data is None:
            return None
        return json.loads(data.decode('utf-8'))

def wait_for_topics(bootstrap_servers, topics, timeout=30):
    """Espera a que todos los topics existan antes de consumir."""
    admin = AdminClient({'bootstrap.servers': bootstrap_servers})
    start_time = time.time()
    while time.time() - start_time < timeout:
        metadata = admin.list_topics(timeout=5)
        existing_topics = metadata.topics.keys()
        if all(topic in existing_topics for topic in topics):
            print(f"[READY] Todos los topics disponibles: {topics}")
            return
        print("[WAIT] Esperando a que los topics estén disponibles...")
        time.sleep(2)
    raise Exception(f"Topics no disponibles después de {timeout} segundos: {topics}")

async def consume():
    print("[START] Iniciando consumidor de Kafka...")
    max_retries = 10
    retry_delay = 5
    topics = ['artist-update', 'album-update', 'user-update', 'song-update']

    consumer_conf = {
        'bootstrap.servers': 'kafka-broker:9092',
        'group.id': 'playlist-consumer-group',
        'auto.offset.reset': 'earliest', #CAMBIAR A EARLIEST EN DEMO
        'key.deserializer': StringDeserializer('utf_8'),
        'value.deserializer': JSONDeserializerClass() 
    }

    for attempt in range(max_retries):
        try:
            print(f"[ATTEMPT] Intento {attempt + 1} de conectar a Kafka...")
            consumer = DeserializingConsumer(consumer_conf)
            wait_for_topics(consumer_conf['bootstrap.servers'], topics)
            consumer.subscribe(topics)
            print("[****] Conectado a Kafka exitosamente!")
            break
        except Exception as e:
            print(f"[ERROR] No se pudo conectar: {e}")
            if attempt < max_retries - 1:
                print(f"[RETRY] Reintentando en {retry_delay} segundos...")
                time.sleep(retry_delay)
            else:
                print("[FAIL] No se pudo conectar después de varios intentos")
                return

    print("[READY] Consumer iniciado, esperando mensajes...")

    try:
        while True:
            msg = consumer.poll(1.0)  
            if msg is None:
                continue
            if msg.error():
                print(f"[ERROR] {msg.error()}")
                continue

            data = msg.value() 
            kafka_key = msg.key()
            topic = msg.topic()
            if topic == 'artist-update':
                print(f"[KEY] {kafka_key}, [MESSAGE] artistId={data.get('artistId')}, name={data.get('name')}")
                await playlist_service.update_artist_from_playlists(data)
            elif topic == 'album-update':
                print(f"[KEY] {kafka_key}, [MESSAGE] albumId={data.get('albumId')}, albumName={data.get('albumName')}")
                await playlist_service.update_album_from_playlists(data)
            elif topic == 'user-update':
                print(f"[KEY] {kafka_key}, [MESSAGE] user_data={data}")
                await playlist_service.update_user_from_playlists(data.get('user'))
            elif topic == 'song-update':
                print(f"[KEY] {kafka_key}, [MESSAGE] songId={data.get('songId')}, songName={data.get('songName')}")
                await playlist_service.update_song_from_playlists(data)
            else:                
                print(f"[WARNING] Mensaje recibido de tópico desconocido: {topic}")
    

    except KeyboardInterrupt:
        print("[STOP] Deteniendo consumer...")

    finally:
        consumer.close()
        print("[CLOSE] Consumer cerrado correctamente.")


if __name__ == "__main__":
    asyncio.run(consume())
