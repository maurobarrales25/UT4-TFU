package AndisUT2.ArtistAPI.service.query;

import AndisUT2.ArtistAPI.repository.query.SongReadRepository;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SongQueryService {

    private final SongReadRepository songReadRepository;

    public SongQueryService(SongReadRepository songReadRepository) {
        this.songReadRepository = songReadRepository;
    }

    public SongView getSongByIDQueryDB(int id) {
        return songReadRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Canción no encontrada con ID: " + id
                ));
    }

    public SongView getSongByNameQueryDB(String name) {
        SongView song = songReadRepository.findBySongName(name);

        if (song == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Canción no encontrada con nombre: " + name
            );
        }

        return song;
    }

    public List<SongView> getAllSongsQueryDB() {return songReadRepository.findAll();}

    public List<SongView> getSongsByArtistIdQueryDB(int artistId) {return songReadRepository.findByArtistId(artistId);}

    public List<SongView> getSongsByAlbumIdQueryDB(int albumId) {return songReadRepository.findByAlbumId(albumId);}


}
