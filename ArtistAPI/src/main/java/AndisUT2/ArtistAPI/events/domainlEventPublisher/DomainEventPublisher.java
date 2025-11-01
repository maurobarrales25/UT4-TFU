package AndisUT2.ArtistAPI.events.domainlEventPublisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public DomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public <T> void publishEvent(T event) {
        publisher.publishEvent(event);
    }
}
