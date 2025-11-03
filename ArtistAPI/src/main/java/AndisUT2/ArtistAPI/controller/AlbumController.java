package AndisUT2.ArtistAPI.controller;

import AndisUT2.ArtistAPI.dto.DTOAlbumCommand;
import AndisUT2.ArtistAPI.model.Album;
import AndisUT2.ArtistAPI.service.command.AlbumService;
import AndisUT2.ArtistAPI.service.query.AlbumQueryService;
import AndisUT2.ArtistAPI.view.AlbumView;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumQueryService albumQueryService;

    @GetMapping("/all-command")
    public ResponseEntity<List<Album>> getAllAlbumsCommand(){
        List<Album> albums = albumService.getAllAlbums();
        return albums.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(albums);
    }

    @GetMapping("all-command-with-info")
    public ResponseEntity<List<DTOAlbumCommand>> getAllAlbumCommandWithInfo(){
        List<DTOAlbumCommand> albumCommands = albumService.getAllAlbumsInfo();
        return albumCommands.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(albumCommands);
    }

    @GetMapping("/all-query")
    public ResponseEntity<List<AlbumView>> getAllAlbumsQuery(){
        List<AlbumView> albums = albumQueryService.getAllAlbumsQueryDB();
        return albums.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(albums);
    }

    @GetMapping("by-id-command")
    public ResponseEntity<DTOAlbumCommand> getAlbumByIdCommand(@RequestParam int id){
        DTOAlbumCommand album = albumService.getAlbumSongs(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("by-id-query")
    public ResponseEntity<AlbumView> getAlbumByIdQuery(@RequestParam int id){
        AlbumView album = albumQueryService.getAlbumByIdQueryDB(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/by-name-command")
    public ResponseEntity<Album> getAlbumByNameCommand(@RequestParam String name){
        Album album = albumService.getAlbumByName(name);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/by-name-query")
    public ResponseEntity<AlbumView> getAlbumByNameQuery(@RequestParam String name){
        AlbumView album = albumQueryService.getAlbumByNameQueryDB(name);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/by-artist-id-command")
    public ResponseEntity<List<Album>> getAlbumByArtistId(@RequestParam int id){
        List<Album> albums = albumService.getAlbumsByArtistId(id);
        return albums.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(albums);
    }

    @GetMapping("/by-artist-id-query")
    public ResponseEntity<List<AlbumView>> getAlbumByArtistIdQuery(@RequestParam int id){
        List<AlbumView> albums = albumQueryService.getAlbumsByArtistId(id);
        return albums.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(albums);
    }

    @PostMapping("/save")
    public ResponseEntity<Album> saveAlbum(@RequestParam String albumName, String artistName){
        Album album = albumService.saveAlbum(albumName, artistName);
        return ResponseEntity.ok(album);
    }

    @PatchMapping("/update-by-id")
    public ResponseEntity<Album> updateAlbumById(@RequestParam int id, @RequestParam String newName){
        Album album = albumService.updateAlbum(id, newName);
        return ResponseEntity.ok(album);
    }

}
