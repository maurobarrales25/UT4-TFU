package AndisUT2.ArtistAPI.controller;

import AndisUT2.ArtistAPI.model.Song;
import AndisUT2.ArtistAPI.service.command.SongService;
import AndisUT2.ArtistAPI.service.query.SongQueryService;
import AndisUT2.ArtistAPI.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongQueryService songQueryService;

    @GetMapping("/all-command")
    public ResponseEntity<List<Song>> getAllSongsCommandDB() {
        List<Song> songs = songService.getAllSongsCommandDB();
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/all-query")
    public ResponseEntity<List<SongView>> getAllSongsQueryDB() {
        List<SongView> songs = songQueryService.getAllSongsQueryDB();
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-id-command")
    public ResponseEntity<Song> getSongByIdCommandDB(@RequestParam int id) {
        Song song = songService.getSongByIdCommand(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/by-id-query")
    public ResponseEntity<SongView> getSongByIdQueryDB(@RequestParam int id) {
        SongView song = songQueryService.getSongByIDQueryDB(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/by-name-command")
    public ResponseEntity<Song> getSongByNameCommandDB(@RequestParam String name) {
        Song song = songService.getSongByNameCommandDB(name);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/by-name-query")
    public ResponseEntity<SongView> getSongByNameQueryDB(@RequestParam String name) {
        SongView song = songQueryService.getSongByNameQueryDB(name);
        return ResponseEntity.ok(song);
    }


    @GetMapping("/by-artist-id-command")
    public ResponseEntity <List<Song>> getSongByArtistIdCommandDB(@RequestParam int artistId) {
        List<Song> songs = songService.getSongsByArtistIdCommandDB(artistId);
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-artist-id-query")
    public ResponseEntity <List<SongView>> getSongByArtistIdQueryDB(@RequestParam int artistId) {
        List<SongView> songs = songQueryService.getSongsByArtistIdQueryDB(artistId);
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-album-id-command")
    public ResponseEntity <List<Song>> getSongByAlbumIdCommandDB(@RequestParam int albumId) {
        List<Song> songs = songService.getSongsByAlbumIdCommandDB(albumId);
        return songs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(songs);
    }

    @GetMapping("/by-album-id-query")
    public ResponseEntity <List<SongView>> getSongByAlbumIdQuerydDB(@RequestParam int albumId) {
        List<SongView> songs = songQueryService.getSongsByAlbumIdQueryDB(albumId);
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
