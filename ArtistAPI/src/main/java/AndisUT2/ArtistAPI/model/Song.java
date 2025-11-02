package AndisUT2.ArtistAPI.model;

public class Song {

    private int songId;
    private String songName;
    private int artistID;
    private int albumID;

    public Song(int songId, String songName, int artistID, int albumID) {
        this.songId = songId;
        this.songName = songName;
        this.artistID = artistID;
        this.albumID = albumID;
    }

    public Song(String songName, int artistID, int albumID) {
        this.songName = songName;
        this.artistID = artistID;
        this.albumID = albumID;
    }

    public Song(){}

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }
}
