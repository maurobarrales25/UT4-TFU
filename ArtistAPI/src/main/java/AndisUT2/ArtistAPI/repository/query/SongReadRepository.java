package AndisUT2.ArtistAPI.repository.query;

import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SongReadRepository extends MongoRepository<SongView, Integer> {

    SongView findBySongName(String songName);

    List<SongView> findByArtistId(Integer artistId);

    List<SongView> findByArtistName(String artistName);

    List<SongView> findByAlbumId(Integer albumId);

}
