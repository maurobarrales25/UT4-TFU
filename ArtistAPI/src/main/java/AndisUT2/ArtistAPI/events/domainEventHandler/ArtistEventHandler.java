package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistUpdateEvent;
import AndisUT2.ArtistAPI.repository.query.ArtistReadRepository;
import AndisUT2.ArtistAPI.view.ArtistView;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Component;

@Component
public class ArtistEventHandler {

    private final ArtistReadRepository artistReadRepository;

    public ArtistEventHandler(ArtistReadRepository artistReadRepository) {
        this.artistReadRepository = artistReadRepository;
    }

    @Async
    @EventListener
    public void handleArtistCreate(DomainArtistCreateEvent artistCreate){
        ArtistView newArtist = new ArtistView(artistCreate.getArtistId(), artistCreate.getName());
        artistReadRepository.save(newArtist);
    }

    @Async
    @EventListener
    public void handleArtistUpdate(DomainArtistUpdateEvent event){
        ArtistView view = artistReadRepository.findById(event.getArtistId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Artista con ID " + event.getArtistId() + " no encontrada."
                        ));
        view.setArtistId(event.getArtistId());
        view.setArtistName(event.getName());
        artistReadRepository.save(view);
    }

}
