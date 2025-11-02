package AndisUT2.ArtistAPI.repository.query;

import AndisUT2.ArtistAPI.view.AlbumView;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlbumReadRepository extends MongoRepository<AlbumView, Integer> {

    List<AlbumView> findByArtistId(Integer artistId);

    AlbumView findByAlbumName(String albumName);
}
