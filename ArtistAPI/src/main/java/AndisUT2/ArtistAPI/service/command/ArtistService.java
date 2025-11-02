package AndisUT2.ArtistAPI.service.command;

import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistCreateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.domainEvents.DomainArtistUpdateEvent;
import AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents.ArtistUpdateEvent;
import AndisUT2.ArtistAPI.events.domainlEventPublisher.DomainEventPublisher;
import AndisUT2.ArtistAPI.events.producer.ArtistProducer;
import AndisUT2.ArtistAPI.mapper.ArtistUpdateMapper;
import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.repository.command.ArtistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistProducer artistProducer;
    private final DomainEventPublisher domainPublisher;

    private static final ArtistUpdateMapper artistUpdateMapper = ArtistUpdateMapper.INSTANCE;

    public ArtistService(ArtistRepository artistRepository, ArtistProducer artistProducer, DomainEventPublisher domainPublisher) {
        this.artistRepository = artistRepository;
        this.artistProducer =  artistProducer;
        this.domainPublisher = domainPublisher;
    }

    public Artist getArtistByIdCommandDB(int id){
        Artist artist = artistRepository.getArtistById(id);

        if(artist == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artista no encontrado con ID: " + id);
        }
        return artist;
    }

    public Artist getArtistByNameCommandDB(String name){
        Artist artist = artistRepository.getArtistByName(name);

        if(artist == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artista no encontrado con nombre: " + name);
        }
        return artist;
    }

    public List<Artist> getAllArtistsCommandDB(){
        return artistRepository.getAllArtists();
    }


    public void publishArtistCreateEvent(Artist artist){
        DomainArtistCreateEvent artistCreate = new DomainArtistCreateEvent(artist.getArtistId(),artist.getName());
        domainPublisher.publishEvent(artistCreate);
    }

    public Artist saveArtist(String name){
        Artist artist = new Artist(name);
        artistRepository.saveArtist(artist);

        publishArtistCreateEvent(artist);

        return artist;
    }

    private void publishArtistUpdateEvents(Artist artist) {
        DomainArtistUpdateEvent domainEvent = new DomainArtistUpdateEvent(artist.getArtistId(), artist.getName());
        domainPublisher.publishEvent(domainEvent);

        ArtistUpdateEvent kafkaEvent = ArtistUpdateMapper.INSTANCE.toKafkaEvent(domainEvent);
        String kafkaKey = "artist-" + artist.getArtistId();
        artistProducer.send("artist-update", kafkaKey, kafkaEvent);
    }


    public Artist updateArtist(int artistId, String newName){
        Artist artist = getArtistByIdCommandDB(artistId);
        artist.setName(newName);
        artistRepository.updateArtist(artist);

        publishArtistUpdateEvents(artist);
        return artist;
    }


}
