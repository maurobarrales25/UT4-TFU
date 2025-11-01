package AndisUT2.ArtistAPI.events.producer;

public interface IEventProducer<T>{

    void send(String topic, String key, T event);
}
