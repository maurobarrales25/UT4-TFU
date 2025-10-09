package AndisUT2.ArtistAPI.Controller;

import AndisUT2.ArtistAPI.Model.Artist;
import AndisUT2.ArtistAPI.Service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/all")
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return artists.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(artists);
    }

    @GetMapping("/by-name")
    public ResponseEntity<Artist> getArtistByName(@RequestParam String name) {
        Artist artist = artistService.getArtistByName(name);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/by-id")
    public ResponseEntity<Artist> getArtistByid(@RequestParam int id) {
        Artist artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/save")
    public ResponseEntity<Artist> saveArtist(@RequestParam String name) {
        Artist artist = artistService.saveArtist(name);
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/update-by-id")
    public ResponseEntity<Artist> updateArtist(@RequestParam int id, @RequestParam String newName) {
        Artist artist = artistService.updateArtist(id,newName);
        return ResponseEntity.ok(artist);
    }

}
