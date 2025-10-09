package AndisUT2.ArtistAPI.events.Producer;


import AndisUT2.ArtistAPI.events.DTOevents.AlbumUpdateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlbumProducer implements IEventProducer<AlbumUpdateEvent> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AlbumProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, String key, AlbumUpdateEvent event) {
        kafkaTemplate.send(topic, key, event);
    }
}
