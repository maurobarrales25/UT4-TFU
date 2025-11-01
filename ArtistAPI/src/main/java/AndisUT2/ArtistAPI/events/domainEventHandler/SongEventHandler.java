package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongUpdateEvent;
import AndisUT2.ArtistAPI.repository.query.SongReadRepository;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Component;

@Component
public class SongEventHandler {

    private final SongReadRepository songReadRepository;

    public SongEventHandler(SongReadRepository songRepository, MongoTemplate mongoTemplate) {
        this.songReadRepository = songRepository;
    }

    @Async
    @EventListener
    public void handleSongCreate(DomainSongCreateEvent songCreate){
        SongView newSong = new SongView(songCreate.getSongId(),songCreate.getSongName(),
                songCreate.getArtistId(), songCreate.getArtistName(), songCreate.getAlbumId(), songCreate.getAlbumName());

        songReadRepository.save(newSong);
    }

    @Async
    @EventListener
    public void handleSongUpdate(DomainSongUpdateEvent songUpdate){
        SongView view = songReadRepository.findById(songUpdate.getSongId())
                .orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Song con ID " + songUpdate.getSongId() + " no encontrada."
                ));
        view.setSongId(songUpdate.getSongId());
        view.setSongName(songUpdate.getSongName());
        view.setArtistId(songUpdate.getArtistId());
        view.setArtistName(songUpdate.getArtistName());
        view.setAlbumId(songUpdate.getAlbumId());
        view.setAlbumName(songUpdate.getAlbumName());

        songReadRepository.save(view);
    }

}
