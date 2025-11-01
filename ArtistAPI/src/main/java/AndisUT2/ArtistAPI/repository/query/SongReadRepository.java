package AndisUT2.ArtistAPI.repository.query;

import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SongReadRepository extends MongoRepository<SongView, Integer> {

    SongView findBySongName(String songName);

    SongView findByArtistId(Integer artistId);

    SongView findByArtistName(String artistName);

}
