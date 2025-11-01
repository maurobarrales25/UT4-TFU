package AndisUT2.ArtistAPI.service;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongUpdateEvent;
import AndisUT2.ArtistAPI.events.domainlEventPublisher.DomainEventPublisher;
import AndisUT2.ArtistAPI.model.Album;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.repository.command.SongRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SongService {

    private SongRepository songRepository;
    private ArtistService artistService;
    private AlbumService albumService;
    private final DomainEventPublisher domainPublisher;

    public SongService(SongRepository songRepository, ArtistService artistService, AlbumService albumService, DomainEventPublisher domainPublisher) {
        this.songRepository = songRepository;
        this.artistService = artistService;
        this.albumService = albumService;
        this.domainPublisher = domainPublisher;
    }

    public Song getSongById(int id) {
        Song song = songRepository.getSongByID(id);

        if(song == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Canción no encontrado con ID: " + id);
        }
        return song;
    }

    public Song getSongByName(String name) {
        Song song = songRepository.getSongByName(name);

        if(song == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Canción no encontrado con nombre: " + name);
        }
        return song;
    }

    public List<Song> getAllSongs() {
        return songRepository.getAllSongs();
    }

    public List<Song> getSongsByArtistId(int artistId) {
        return songRepository.getSongsByArtistID(artistId);
    }

    public List<Song> getSongsByAlbumId(int albumId) {
        return songRepository.getSongsByAlbumID(albumId);
    }


    public void publishSongCreate(Song song, Artist artist, Album album) {
        DomainSongCreateEvent songCreate = new DomainSongCreateEvent(
                song.getSongID(), song.getSongName(),
                song.getArtistID(),artist.getName(),
                album.getAlbumId(), album.getAlbumName());

        domainPublisher.publishEvent(songCreate);
    }


    public Song saveSong(String name, int artistId, int albumId) {
        Artist artist;
        Album album;

        try {
            artist = artistService.getArtistById(artistId);
            album = albumService.getAlbumById(albumId);
        } catch (RuntimeException e) {
            throw new RuntimeException("No se puede crear la canción: " + e.getMessage());
        }

        Song song = new Song(name, artistId, albumId);
        songRepository.saveSong(song);

        publishSongCreate(song, artist, album);
        return song;
    }

    private void publishSongUpdate(Song song){
        Artist artist;
        Album album;

        try {
            artist = artistService.getArtistById(song.getArtistID());
            album = albumService.getAlbumById(song.getAlbumID());
        } catch (RuntimeException e) {
            throw new RuntimeException("No se puede actualizar  la canción: " + e.getMessage());
        }

        DomainSongUpdateEvent event = new DomainSongUpdateEvent();
        event.setSongId(song.getSongID());
        event.setSongName(song.getSongName());
        event.setArtistId(song.getArtistID());
        event.setArtistName(artist.getName());
        event.setAlbumId(song.getAlbumID());
        event.setAlbumName(album.getAlbumName());
        domainPublisher.publishEvent(event);
    }


    public Song updateSong(String name, int songID) {
        Song song;

        try {
            song = getSongById(songID);
        } catch (RuntimeException e) {
            throw new RuntimeException("No se puede actualizar  la canción: " + e.getMessage());
        }

        song.setSongName(name);
        songRepository.updateSong(song);
        publishSongUpdate(song);
        return song;
    }
}
