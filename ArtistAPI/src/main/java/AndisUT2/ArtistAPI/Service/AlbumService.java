package AndisUT2.ArtistAPI.Service;

import AndisUT2.ArtistAPI.Model.Album;
import AndisUT2.ArtistAPI.Model.Artist;
import AndisUT2.ArtistAPI.Repository.AlbumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final ArtistService artistService;

    public AlbumService(AlbumRepository albumRepository, ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
    }

    public Album getAlbumByName(String name){
        Album album = albumRepository.getAlbumByName(name);
        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Album no encontrado con nombre: " + name);
        }
        return album;
    }

    public Album getAlbumById(int id){
        Album album = albumRepository.getAlbumById(id);

        if(album == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Album no encontrado con id: " + id);
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

       Album album = new Album(name, artist.getArtistID());
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
        return albumRepository.updateAlbum(album);
    }

}
