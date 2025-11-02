package AndisUT2.ArtistAPI.events.DTOevents.domainEvents;

public class DomainAlbumCreateEvent {

    private int albumId;
    private String albumName;
    private int artistId;
    private String artistName;

    public DomainAlbumCreateEvent(){}

    public DomainAlbumCreateEvent(int albumId, String albumName, int artistId, String artistName) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
