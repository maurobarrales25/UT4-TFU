package AndisUT2.ArtistAPI.repository.query;

import AndisUT2.ArtistAPI.view.ArtistView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistReadRepository extends MongoRepository<ArtistView, Integer> {

    ArtistView findByArtistName(String artistName);
}
