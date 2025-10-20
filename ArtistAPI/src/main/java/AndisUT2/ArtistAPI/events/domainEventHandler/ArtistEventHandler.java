package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistCreate;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistUpdateEvent;
import AndisUT2.ArtistAPI.repository.read.ArtistReadRepository;
import AndisUT2.ArtistAPI.view.ArtistView;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;

public class ArtistEventHandler {

    private final ArtistReadRepository artistReadRepository;
    private final MongoTemplate mongoTemplate;

    public ArtistEventHandler(ArtistReadRepository artistReadRepository, MongoTemplate mongoTemplate) {
        this.artistReadRepository = artistReadRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Async
    @EventListener
    public void handleArtistCreate(DomainArtistCreate artistCreate){
        ArtistView newArtist = new ArtistView(artistCreate.getArtistId(), artistCreate.getName());
        artistReadRepository.save(newArtist);
    }

    @Async
    @EventListener
    public void handleArtistUpdate(DomainArtistUpdateEvent event){
        ArtistView view = artistReadRepository.findById(event.getArtistId())
                .orElse(new ArtistView(event.getArtistId(), event.getName()));

        view.setArtistId(event.getArtistId());
        view.setName(event.getName());
        artistReadRepository.save(view);
    }

}
