package AndisUT2.ArtistAPI.events.DTOevents.domainEvents;

public class DomainArtistUpdateEvent {

    private int artistId;
    private String name;

    public DomainArtistUpdateEvent() {}

    public DomainArtistUpdateEvent(int artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
