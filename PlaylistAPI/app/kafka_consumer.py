from confluent_kafka import DeserializingConsumer
from confluent_kafka.serialization import StringDeserializer, Deserializer
import json
import time

class JSONDeserializerClass(Deserializer):
    def __init__(self):
        super().__init__()

    def __call__(self, data, ctx):
        if data is None:
            return None
        return json.loads(data.decode('utf-8'))

def consume_artists():
    print("[START] Iniciando consumidor de Kafka...")
    max_retries = 10
    retry_delay = 5

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
            consumer.subscribe(['artist-update','album-update'])
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
                print(f"[KEY] {kafka_key}, [MESSAGE] artistID={data.get('artistID')}, name={data.get('name')}")
            elif topic == 'album-update':
                print(f"[KEY] {kafka_key}, [MESSAGE] albumId={data.get('albumId')}, albumName={data.get('albumName')}, artistId={data.get('artistId')}, artistName={data.get('artistName')}")
            else:                
                print(f"[WARNING] Mensaje recibido de tópico desconocido: {topic}")
    

    except KeyboardInterrupt:
        print("[STOP] Deteniendo consumer...")

    finally:
        consumer.close()
        print("[CLOSE] Consumer cerrado correctamente.")


def consume_users():
    print("[START] Iniciando consumidor de usuarios...")
    consumer_conf = {
        'bootstrap.servers': 'kafka-broker:9092',
        'group.id': 'playlist-user-consumer-group',
        'auto.offset.reset': 'earliest',
        'key.deserializer': StringDeserializer('utf_8'),
        'value.deserializer': JSONDeserializerClass()
    }

    consumer = DeserializingConsumer(consumer_conf)
    consumer.subscribe(['user-updates'])
    print("[READY] Consumer de usuarios iniciado...")

    try:
        while True:
            msg = consumer.poll(1.0)
            if msg is None:
                continue
            if msg.error():
                print(f"[ERROR] {msg.error()}")
                continue

    except KeyboardInterrupt:
        print("[STOP] Deteniendo consumer de usuarios...")

    finally:
        consumer.close()
        print("[CLOSE] Consumer de usuarios cerrado correctamente.")

if __name__ == "__main__":
    consume_artists()
    consume_users()
