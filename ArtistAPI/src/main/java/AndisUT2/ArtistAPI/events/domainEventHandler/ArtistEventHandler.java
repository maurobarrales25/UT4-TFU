package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistUpdateEvent;
import AndisUT2.ArtistAPI.repository.query.ArtistReadRepository;
import AndisUT2.ArtistAPI.repository.query.AlbumReadRepository;
import AndisUT2.ArtistAPI.repository.query.SongReadRepository;
import AndisUT2.ArtistAPI.view.ArtistView;
import AndisUT2.ArtistAPI.view.AlbumView;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ArtistEventHandler {

    private final ArtistReadRepository artistReadRepository;
    private final AlbumReadRepository albumReadRepository;
    private final SongReadRepository songReadRepository;


    public ArtistEventHandler(ArtistReadRepository artistReadRepository, AlbumReadRepository albumReadRepository, SongReadRepository songReadRepository) {
        this.artistReadRepository = artistReadRepository;
        this.albumReadRepository = albumReadRepository;
        this.songReadRepository = songReadRepository;
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

        List<AlbumView> albums = albumReadRepository.findByArtistId(event.getArtistId());
        for (AlbumView album : albums) {
            album.setArtistName(event.getName());
            albumReadRepository.save(album);
        }

        List<SongView> songs = songReadRepository.findByArtistId(event.getArtistId());
        for (SongView song : songs) {
            song.setArtistName(event.getName());
            songReadRepository.save(song);
        }
    }

}
