package AndisUT2.ArtistAPI.Service;

import AndisUT2.ArtistAPI.Model.Artist;
import AndisUT2.ArtistAPI.Repository.ArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) { this.artistRepository = artistRepository; }

    public Artist getArtistByName(String name){
        Artist artist = artistRepository.getArtistByName(name);

        if(artist == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artista no encontrado con nombre: " + name);
        }
        return artist;
    }

    public List<Artist> getAllArtists(){
        return artistRepository.getAllArtists();
    }

    public Artist getArtistById(int id){
        Artist artist = artistRepository.getArtistById(id);

        if(artist == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artista no encontrado con ID: " + id);
        }
        return artist;
    }

    public Artist saveArtist(String name){
        Artist artist = new Artist(name);
        return artistRepository.saveArtist(artist);

    }

    public Artist updateArtist(int artistId, String newName){
        Artist artist = getArtistById(artistId);
        artist.setName(newName);
        return artistRepository.updateArtist(artist);
    }
}
