package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainAlbumCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainAlbumUpdateEvent;
import AndisUT2.ArtistAPI.repository.query.AlbumReadRepository;
import AndisUT2.ArtistAPI.view.AlbumView;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Component;

@Component
public class AlbumEventHandler {

    private final AlbumReadRepository albumReadRepository;

    public AlbumEventHandler(AlbumReadRepository albumReadRepository) {
        this.albumReadRepository = albumReadRepository;
    }

    @Async
    @EventListener
    public void handleAlbumCreate(DomainAlbumCreateEvent event) {
        AlbumView albumView = new AlbumView();
        albumView.setAlbumId(event.getAlbumId());
        albumView.setAlbumName(event.getAlbumName());
        albumView.setArtistId(event.getArtistId());
        albumView.setArtistName(event.getArtistName());
        albumReadRepository.save(albumView);
    }

    @Async
    @EventListener
    public void handleAlbumUpdate(DomainAlbumUpdateEvent event) {
        AlbumView view = albumReadRepository.findById(event.getAlbumId())
                .orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Album con ID " + event.getAlbumId() + " no encontrada."
                ));
        view.setAlbumId(event.getAlbumId());
        view.setAlbumName(event.getAlbumName());
        view.setArtistId(event.getArtistId());
        view.setArtistName(event.getArtistName());
        albumReadRepository.save(view);
    }
}
