package AndisUT2.ArtistAPI.model;

public class Artist {

    private int artistID;
    private String name;

    public Artist(int artistID, String name) {
        this.artistID = artistID;
        this.name = name;
    }

    public Artist(String name){
        this.name = name;
    }

    public Artist() {}

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
