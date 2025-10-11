package AndisUT2.ArtistAPI.controller;

import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/all")
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-id")
    public ResponseEntity<Song> getSongById(@RequestParam int id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/by-name")
    public ResponseEntity<Song> getSongByName(@RequestParam String name) {
        Song song = songService.getSongByName(name);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/by-artist-id")
    public ResponseEntity <List<Song>> getSongByArtistId(@RequestParam int artistId) {
        List<Song> songs = songService.getSongsByArtistId(artistId);
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-album-id")
    public ResponseEntity <List<Song>> getSongByAlbumId(@RequestParam int albumId) {
        List<Song> songs = songService.getSongsByAlbumId(albumId);
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @PostMapping("/save")
    public ResponseEntity<Song> saveSong(@RequestParam String name, @RequestParam int artistId, @RequestParam int albumId) {
        Song song =songService.saveSong(name,artistId,albumId);
        return ResponseEntity.ok(song);
    }

    @PatchMapping("/update-title")
    public ResponseEntity<Song> updateSongTitle(@RequestParam String name, @RequestParam int songId) {
        Song song = songService.updateSong(name, songId);
        return ResponseEntity.ok(song);
    }

}
