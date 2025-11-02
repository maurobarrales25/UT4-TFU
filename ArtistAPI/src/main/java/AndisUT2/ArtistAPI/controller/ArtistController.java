package AndisUT2.ArtistAPI.controller;

import AndisUT2.ArtistAPI.model.Artist;
import AndisUT2.ArtistAPI.service.command.ArtistService;
import AndisUT2.ArtistAPI.service.query.ArtistQueryService;
import AndisUT2.ArtistAPI.view.ArtistView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistQueryService artistQueryService;

    @GetMapping("/all-command")
    public ResponseEntity<List<Artist>> getAllArtistsCommand() {
        List<Artist> artists = artistService.getAllArtistsCommandDB();
        return artists.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(artists);
    }

    @GetMapping("/all-query")
    public ResponseEntity<List<ArtistView>> getAllArtistsQuery() {
        List<ArtistView> artists = artistQueryService.getAllArtistsQueryDB();
        return artists.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(artists);
    }

    @GetMapping("/by-name-command")
    public ResponseEntity<Artist> getArtistByNameCommand(@RequestParam String name) {
        Artist artist = artistService.getArtistByNameCommandDB(name);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/by-name-query")
    public ResponseEntity<ArtistView> getArtistByNameQuery(@RequestParam String name) {
        ArtistView artist = artistQueryService.getArtistByNameQueryDB(name);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/by-id-command")
    public ResponseEntity<Artist> getArtistByid(@RequestParam int id) {
        Artist artist = artistService.getArtistByIdCommandDB(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/by-id-query")
    public ResponseEntity<ArtistView> getArtistByidQuery(@RequestParam int id) {
        ArtistView artist = artistQueryService.getArtistByIdQueryDB(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/save")
    public ResponseEntity<Artist> saveArtist(@RequestParam String name) {
        Artist artist = artistService.saveArtist(name);
        return ResponseEntity.ok(artist);
    }

    @PatchMapping("/update-by-id")
    public ResponseEntity<Artist> updateArtist(@RequestParam int id, @RequestParam String newName) {
        Artist artist = artistService.updateArtist(id,newName);
        return ResponseEntity.ok(artist);
    }

}
