package AndisUT2.ArtistAPI.events.DTOevents;

public class ArtistUpdateEvent {

    private int artistID;
    private String name;

    public ArtistUpdateEvent(int artistID, String name) {
        this.artistID = artistID;
        this.name = name;
    }

    public ArtistUpdateEvent(String name){
        this.name = name;
    }

    public ArtistUpdateEvent() {}

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
