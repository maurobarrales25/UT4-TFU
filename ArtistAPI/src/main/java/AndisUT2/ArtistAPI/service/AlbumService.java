package AndisUT2.ArtistAPI.service;

import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.AlbumUpdateEvent;
import AndisUT2.ArtistAPI.events.producer.AlbumProducer;
import AndisUT2.ArtistAPI.model.Album;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.repository.write.AlbumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistService artistService;
    private final AlbumProducer albumProducer;


    public AlbumService(AlbumRepository albumRepository, ArtistService artistService, AlbumProducer albumProducer) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
        this.albumProducer = albumProducer;
    }

    public Album getAlbumByName(String name){
        Album album = albumRepository.getAlbumByName(name);
        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontro Album con nombre: " + name);
        }
        return album;
    }

    public Album getAlbumById(int id){
        Album album = albumRepository.getAlbumById(id);

        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontro Album con id: " + id);
        }
        return album;
    }


    public List<Album> getAllAlbums(){
        return albumRepository.getAllAlbums();
    }

    public List<Album> getAlbumsByArtistId(int artistId){
        return albumRepository.getAlbumsByArtistId(artistId);
    }

    public Album saveAlbum(String name, String artistName){
       Artist artist = artistService.getArtistByName(artistName);

       if(artist == null){
           artist = artistService.saveArtist(artistName);
       }

       Album album = new Album(name, artist.getArtistId());
       return albumRepository.saveAlbum(album);
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

        Artist artist = artistService.getArtistById(album.getArtistId());
        AlbumUpdateEvent event= new AlbumUpdateEvent(album.getAlbumId(),album.getAlbumName(),
                artist.getArtistId(), artist.getName());

        String kafkaKey = String.format("album-%d", album.getAlbumId());
        albumProducer.send("album-update", kafkaKey, event);

        return album;
    }

}
