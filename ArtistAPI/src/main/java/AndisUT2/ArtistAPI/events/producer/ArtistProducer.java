package AndisUT2.ArtistAPI.events.producer;

import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.ArtistUpdateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtistProducer implements IEventProducer<ArtistUpdateEvent> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ArtistProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, String key, ArtistUpdateEvent event) {
        kafkaTemplate.send(topic, key, event);
    }


}
