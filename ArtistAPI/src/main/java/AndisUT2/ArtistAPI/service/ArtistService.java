package AndisUT2.ArtistAPI.service;

import AndisUT2.ArtistAPI.events.DTOevents.ArtistUpdateEvent;
import AndisUT2.ArtistAPI.events.Producer.ArtistProducer;
import AndisUT2.ArtistAPI.events.Producer.IEventProducer;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.repository.ArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistProducer artistProducer;

    public ArtistService(ArtistRepository artistRepository, ArtistProducer artistProducer) {
        this.artistRepository = artistRepository;
        this.artistProducer =  artistProducer;
    }

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
        artistRepository.updateArtist(artist);

        ArtistUpdateEvent event = new ArtistUpdateEvent(artist.getArtistID(), artist.getName());
        String kafkaKey = String.format("artist-%d", artist.getArtistID());
        artistProducer.send("artist-update", kafkaKey, event);
        return artist;
    }
}
