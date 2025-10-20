package AndisUT2.ArtistAPI.events.DTOevents.kafkaEvents;

public class ArtistUpdateEvent {

    private int artistId;
    private String name;

    public ArtistUpdateEvent() {}

    public ArtistUpdateEvent(int artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public ArtistUpdateEvent(String name){
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
