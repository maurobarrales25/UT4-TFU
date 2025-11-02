package AndisUT2.ArtistAPI.events.DTOevents.domainEvents;

public class DomainArtistCreateEvent {
    private Integer artistId;
    private String name;

    public DomainArtistCreateEvent() {}

    public DomainArtistCreateEvent(Integer artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
