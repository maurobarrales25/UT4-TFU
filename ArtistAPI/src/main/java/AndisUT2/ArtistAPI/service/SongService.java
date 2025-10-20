package AndisUT2.ArtistAPI.service;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainSongCreate;
import AndisUT2.ArtistAPI.events.domainlEventPublisher.DomainEventPublisher;
import AndisUT2.ArtistAPI.model.Album;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.repository.write.SongRepository;
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

    public SongService(SongRepository songRepository, ArtistService artistService,
                       AlbumService albumService, DomainEventPublisher domainPublisher) {
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
        DomainSongCreate songCreate = new DomainSongCreate(
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

    public Song updateSong(String name, int songID) {
        Song song = getSongById(songID);
        song.setSongName(name);
        return songRepository.updateSong(song);
    }
}
