package AndisUT2.ArtistAPI.service.command;

import AndisUT2.ArtistAPI.dto.DTOAlbumCommand;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainAlbumCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainAlbumUpdateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.AlbumUpdateEvent;
import AndisUT2.ArtistAPI.events.domainlEventPublisher.DomainEventPublisher;
import AndisUT2.ArtistAPI.events.producer.AlbumProducer;
import AndisUT2.ArtistAPI.model.Album;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.repository.command.AlbumRepository;
import AndisUT2.ArtistAPI.repository.command.SongRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;
    private final AlbumProducer albumProducer;
    private final DomainEventPublisher domainEventPublisher;
    private final SongRepository songRepository;
    private final MeterRegistry meterRegistry;

    public AlbumService(AlbumRepository albumRepository, ArtistService artistService, AlbumProducer albumProducer, DomainEventPublisher domainEventPublisher, SongRepository songRepository, MeterRegistry meterRegistry) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
        this.albumProducer = albumProducer;
        this.domainEventPublisher = domainEventPublisher;
        this.songRepository = songRepository;
        this.meterRegistry = meterRegistry;
    }

    @Timed(
            value = "command.album.getById",
            description = "Tiempo de consulta de album por ID en Command DB",
            percentiles = {0.5, 0.95, 0.99}
    )
    public Album getAlbumById(int id){
        Album album = albumRepository.getAlbumById(id);

        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontro Album con id: " + id);
        }
        return album;
    }

    @Timed(
            value = "command.album.getByName",
            description = "Tiempo de consulta de album por nombre en Command DB",
            percentiles = {0.5, 0.95, 0.99}
    )
    public Album getAlbumByName(String name){
        Album album = albumRepository.getAlbumByName(name);
        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontro Album con nombre: " + name);
        }
        return album;
    }

    public List<Album> getAllAlbums(){
        return albumRepository.getAllAlbums();
    }

    @Timed(
            value = "command.album.allWinfo",
            description = "Tiempo de consulta de albums con toda la informacion en Command DB",
            percentiles = {0.5, 0.95, 0.99}
    )
    public List<DTOAlbumCommand> getAllAlbumsInfo() {
        List<DTOAlbumCommand> albums = albumRepository.getAllAlbumsWithArtists();

        for (DTOAlbumCommand album : albums) {
            List<Song> songs = songRepository.getSongsByAlbumID(album.getAlbumId());
            album.setSongs(songs);
        }

        return albums;
    }

    @Timed(
            value = "command.album.getByArtistId",
            description = "Tiempo de consulta de albums por artista en Command DB",
            percentiles = {0.5, 0.95, 0.99}
    )
    public List<Album> getAlbumsByArtistId(int artistId){
        return albumRepository.getAlbumsByArtistId(artistId);
    }

    @Timed(
            value = "command.album.getWithSongs",
            description = "Tiempo de consulta de album con canciones usando JOINS en Command DB",
            percentiles = {0.5, 0.95, 0.99},
            extraTags = {"database", "command", "operation", "join"}
    )
    public DTOAlbumCommand getAlbumSongs(int albumId){
        DTOAlbumCommand dto = albumRepository.getAlbumWithArtistById(albumId);

        List<Song> songs = songRepository.getSongsByAlbumID(albumId);
        dto.setSongs(songs);
        return dto;
    }

    private void publishAlbumCreateEvent(Album album){
        DomainAlbumCreateEvent albumCreate = new DomainAlbumCreateEvent();
        Artist artist = artistService.getArtistByIdCommandDB(album.getArtistId());

        albumCreate.setAlbumId(album.getAlbumId());
        albumCreate.setAlbumName(album.getAlbumName());
        albumCreate.setArtistId(artist.getArtistId());
        albumCreate.setArtistName(artist.getName());
        domainEventPublisher.publishEvent(albumCreate);
    }

    public Album saveAlbum(String name, String artistName){
       Artist artist = artistService.getArtistByNameCommandDB(artistName);

       Album album = new Album(name, artist.getArtistId());
       albumRepository.saveAlbum(album);
       publishAlbumCreateEvent(album);
       return album;
    }

    private void publishAlbumUpdateEvent(Album album){
        DomainAlbumUpdateEvent update = new DomainAlbumUpdateEvent();
        Artist artist = artistService.getArtistByIdCommandDB(album.getArtistId());

        update.setAlbumId(album.getAlbumId());
        update.setAlbumName(album.getAlbumName());
        update.setArtistId(artist.getArtistId());
        update.setArtistName(artist.getName());
        domainEventPublisher.publishEvent(update);
    }

    public Album updateAlbum(int albumId, String newName){
        Album album = albumRepository.getAlbumById(albumId);
        if (album == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Album con ID " + albumId + " no encontrado para actualizar.");
        }
        album.setAlbumName(newName);
        albumRepository.updateAlbum(album);

        Artist artist = artistService.getArtistByIdCommandDB(album.getArtistId());
        AlbumUpdateEvent event= new AlbumUpdateEvent(album.getAlbumId(),album.getAlbumName(),
                artist.getArtistId(), artist.getName());

        publishAlbumUpdateEvent(album);

        String kafkaKey = String.format("album-%d", album.getAlbumId());
        albumProducer.send("album-update", kafkaKey, event);

        return album;
    }

}
