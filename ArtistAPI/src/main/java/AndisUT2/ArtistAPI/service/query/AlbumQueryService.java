package AndisUT2.ArtistAPI.service.query;


import AndisUT2.ArtistAPI.repository.query.AlbumReadRepository;
import AndisUT2.ArtistAPI.view.AlbumView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlbumQueryService {

    private final AlbumReadRepository albumReadRepository;

    public AlbumQueryService(AlbumReadRepository albumReadRepository) {
        this.albumReadRepository = albumReadRepository;
    }

    public AlbumView getAlbumByIdQueryDB(int id){
        return albumReadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Album no encontrado con ID: " + id
                ));
    }

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

    public List<AlbumView> getAllAlbumsQueryDB(){return albumReadRepository.findAll();}

    public List<AlbumView> getAlbumsByArtistId(int artistId){return albumReadRepository.findByArtistId(artistId);}


}
