package AndisUT2.ArtistAPI.service.query;


import AndisUT2.ArtistAPI.repository.query.AlbumReadRepository;
import AndisUT2.ArtistAPI.view.AlbumView;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlbumQueryService {

    private final AlbumReadRepository albumReadRepository;
    private final MeterRegistry meterRegistry;

    public AlbumQueryService(AlbumReadRepository albumReadRepository, MeterRegistry meterRegistry) {
        this.albumReadRepository = albumReadRepository;
        this.meterRegistry = meterRegistry;
    }

    @Timed(
            value = "query.album.getById",
            description = "Tiempo de consulta de album por ID desde vista materializada (Query DB)",
            percentiles = {0.5, 0.95, 0.99}
    )
    public AlbumView getAlbumByIdQueryDB(int id){
        return albumReadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Album no encontrado con ID: " + id
                ));
    }

    @Timed(
            value = "query.album.getByName",
            description = "Tiempo de consulta de album por nombre desde vista materializada (Query DB)",
            percentiles = {0.5, 0.95, 0.99}
    )
    public AlbumView getAlbumByNameQueryDB(String name) {
        AlbumView album = albumReadRepository.findByAlbumName(name);
        if (album == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Album no encontrado con nombre: " + name
            );
        }
        return album;
    }

    @Timed(
            value = "query.album.getAll",
            description = "Tiempo de consulta de todos los álbumes desde vista materializada (Query DB)",
            percentiles = {0.5, 0.95, 0.99}
    )
    public List<AlbumView> getAllAlbumsQueryDB(){return albumReadRepository.findAll();}

    @Timed(
            value = "query.album.getByArtist",
            description = "Tiempo de consulta de álbumes por artista desde vista materializada (Query DB)",
            percentiles = {0.5, 0.95, 0.99}
    )
    public List<AlbumView> getAlbumsByArtistId(int artistId){return albumReadRepository.findByArtistId(artistId);}


}
