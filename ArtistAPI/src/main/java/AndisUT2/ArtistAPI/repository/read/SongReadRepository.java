package AndisUT2.ArtistAPI.repository.read;

import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongReadRepository extends MongoRepository<SongView, Integer> {

    SongView findByName(String name);

    SongView findByArtistId(Integer artistId);

    SongView findByArtistName(String artist);

}
