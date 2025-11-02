package AndisUT2.ArtistAPI.events.producer;

import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.SongUpdatedEvent;
import jakarta.validation.OverridesAttribute;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SongProducer implements IEventProducer<SongUpdatedEvent> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SongProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, String key, SongUpdatedEvent event) {
        kafkaTemplate.send(topic, key, event);
    }
}
