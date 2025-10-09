package AndisUT2.ArtistAPI.events.Producer;

public interface IEventProducer<T>{

    void send(String topic, String key, T event);
}
