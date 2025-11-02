package AndisUT2.ArtistAPI.service.query;

import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.repository.query.ArtistReadRepository;
import AndisUT2.ArtistAPI.view.ArtistView;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArtistQueryService {

    private final ArtistReadRepository artistReadRepository;

    public ArtistQueryService(ArtistReadRepository artistReadRepository) {
        this.artistReadRepository = artistReadRepository;
    }

    public ArtistView getArtistByIdQueryDB(int id){
        return artistReadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Artista no encontrado con ID: " + id
                ));
    }

    public ArtistView getArtistByNameQueryDB(String name) {
        ArtistView artist = artistReadRepository.findByArtistName(name);

        if (artist == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Artista no encontrado con nombre: " + name
            );
        }

        return artist;
    }

    public List<ArtistView> getAllArtistsQueryDB(){
        return artistReadRepository.findAll();
    }


}
