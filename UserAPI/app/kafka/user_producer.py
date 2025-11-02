from confluent_kafka import Producer
import json
import os

producer_conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP_SERVERS", "kafka-broker:9092")
}
producer = Producer(producer_conf)

def send_user_event(event_type: str, user_data: dict):
    topic = "user-update"
    message = {
        "event": event_type,
        "user": user_data
    }

    try:
        producer.produce(topic, value=json.dumps(message))
        producer.flush()
        print(f"Evento publicado {topic}: {message}") # log de prueba
    except Exception as e:
        print(f"Error enviando mensaje Kafka: {e}")
