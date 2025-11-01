package AndisUT2.ArtistAPI.view;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "albums")
public class AlbumView {
    @Id
    private int albumId;
    private String albumName;
    private int artistId;
    private String artistName;
    private List<SongEmbedded> songs;

    public AlbumView(int albumId, String albumName, int artistId, String artistName, List<SongEmbedded> songs) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
        this.songs = songs;
    }

    public AlbumView() {}

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

    public List<SongEmbedded> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEmbedded> songs) {
        this.songs = songs;
    }

    public static class SongEmbedded {
        private int songID;
        private String songName;

        public SongEmbedded(int songID, String songName) {
            this.songID = songID;
            this.songName = songName;
        }

        public SongEmbedded(){}

        public int getSongID() {
            return songID;
        }

        public void setSongID(int songID) {
            this.songID = songID;
        }

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }
    }
}
