package AndisUT2.ArtistAPI.view;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "artists")
public class ArtistView {

    @Id
    private Integer artistId;
    private String artistName;

    public ArtistView(Integer artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
