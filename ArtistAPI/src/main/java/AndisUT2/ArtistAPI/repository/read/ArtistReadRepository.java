package AndisUT2.ArtistAPI.repository.read;

import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.view.ArtistView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistReadRepository extends MongoRepository<ArtistView, Integer> {

    ArtistView findByArtistName(String artistName);

}
