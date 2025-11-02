package AndisUT2.ArtistAPI.dto;

import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.view.AlbumView;

import java.util.List;

public class DTOAlbumCommand {

    private int albumId;
    private String albumName;
    private int artistId;
    private String artistName;
    private List<Song> songs;

    public DTOAlbumCommand(int albumId, String albumName, int artistId, String artistName, List<Song> songs) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.songs = songs;
    }

    public DTOAlbumCommand() {}

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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
