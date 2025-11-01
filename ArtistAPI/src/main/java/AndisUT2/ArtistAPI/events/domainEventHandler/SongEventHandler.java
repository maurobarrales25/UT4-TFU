package AndisUT2.ArtistAPI.events.domainEventHandler;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongUpdateEvent;
import AndisUT2.ArtistAPI.repository.query.SongReadRepository;
import AndisUT2.ArtistAPI.repository.query.AlbumReadRepository;
import AndisUT2.ArtistAPI.view.AlbumView;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.ArrayList;

@Component
public class SongEventHandler {

    private final SongReadRepository songReadRepository;
    private final AlbumReadRepository albumReadRepository;

    public SongEventHandler(SongReadRepository songRepository, AlbumReadRepository albumReadRepository) {
        this.songReadRepository = songRepository;
        this.albumReadRepository = albumReadRepository;
    }

    @Async
    @EventListener
    public void handleSongCreate(DomainSongCreateEvent songCreate){
        SongView newSong = new SongView(songCreate.getSongId(),songCreate.getSongName(),
                songCreate.getArtistId(), songCreate.getArtistName(), songCreate.getAlbumId(), songCreate.getAlbumName());
        songReadRepository.save(newSong);

        AlbumView album = albumReadRepository.findById(songCreate.getAlbumId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album no encontrado"));

        if (album.getSongs() != null) {
            album.getSongs().add(new AlbumView.SongEmbedded(songCreate.getSongId(), songCreate.getSongName()));
        } 
        else {
            List<AlbumView.SongEmbedded> songs = new ArrayList<>();
            songs.add(new AlbumView.SongEmbedded(songCreate.getSongId(), songCreate.getSongName()));
            album.setSongs(songs);
        }

        albumReadRepository.save(album);
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
        view.setSongName(songUpdate.getSongName());
        view.setArtistId(songUpdate.getArtistId());
        view.setArtistName(songUpdate.getArtistName());
        view.setAlbumId(songUpdate.getAlbumId());
        view.setAlbumName(songUpdate.getAlbumName());

        AlbumView album = albumReadRepository.findById(songUpdate.getAlbumId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album no encontrado"));

        if (album.getSongs() != null) {
            for (AlbumView.SongEmbedded s : album.getSongs()) {
                if (s.getSongID() == songUpdate.getSongId()) {
                    s.setSongName(songUpdate.getSongName());
                }
            }
        }

        albumReadRepository.save(album);
        songReadRepository.save(view);
    }
}
