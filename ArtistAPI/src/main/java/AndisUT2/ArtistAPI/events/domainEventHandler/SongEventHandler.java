package AndisUT2.ArtistAPI.events.domainEventHandler;


import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongCreate;
import AndisUT2.ArtistAPI.repository.read.SongReadRepository;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;

public class SongEventHandler {

    private final SongReadRepository songReadRepository;
    private final MongoTemplate mongoTemplate;

    public SongEventHandler(SongReadRepository songRepository, MongoTemplate mongoTemplate) {
        this.songReadRepository = songRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Async
    @EventListener
    public void handleSongCreate(DomainSongCreate songCreate){
        SongView newSong = new SongView(songCreate.getSongId(),songCreate.getSongName(),
                songCreate.getArtistId(), songCreate.getArtistName(), songCreate.getAlbumId(), songCreate.getAlbumName());

        songReadRepository.save(newSong);
    }

}
