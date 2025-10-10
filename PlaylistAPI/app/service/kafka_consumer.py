from quixstreams import Application
import asyncio

async def consume_artists():
    app = Application(
        broker_addresses=["broker1:9092", "broker2:9092"],
        log_level="INFO", 
        consumer_group="playlist-consumer-group"
    )

    consumer = app.getConsumer()
    consumer.subscribe(["artist-update"])

    print("Consumer iniciado, esperando mensajes...")

    while True:
        message = consumer.poll(1)  

        if message is None:
            await asyncio.sleep(0.1)  
        elif message.error():
            print(f"Error: {message.error()}")
        else:
            print("Mensaje recibido:", message.value)
            
