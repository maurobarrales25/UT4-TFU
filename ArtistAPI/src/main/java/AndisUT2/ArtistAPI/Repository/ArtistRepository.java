package AndisUT2.ArtistAPI.Repository;

import AndisUT2.ArtistAPI.Model.Artist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ArtistRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Artist> artistRowMapper = (rs, rowNum) -> {
        Artist artist = new Artist();
        artist.setArtistID(rs.getInt("artist_id"));
        artist.setName(rs.getString("name"));
        return artist;
    };

    public ArtistRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public Artist getArtistByName(String name) {
        String sql = "select * from artist where name = ?";
        return jdbcTemplate.queryForObject(sql, artistRowMapper, name);
    }

    public Artist getArtistById(int artistID) {
        String sql = "select * from artist where artist_id = ?";
        return jdbcTemplate.queryForObject(sql, artistRowMapper, artistID);
    }

    public List<Artist> getAllArtists() {
        String sql = "select * from artist";
        return jdbcTemplate.query(sql, artistRowMapper);
    }

    public Artist saveArtist(Artist artist) {
        String sql = "INSERT INTO artist (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, artist.getName());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            artist.setArtistID(key.intValue());
        } else {
            throw new RuntimeException("No se pudo obtener el ID generado del artista");
        }

        return artist;
    }

    public Artist updateArtist(Artist artist) {
        String sql = "UPDATE artist SET name = ? WHERE artist_id = ?";
        int rows = jdbcTemplate.update(sql, artist.getName(), artist.getArtistID());

        if (rows > 0) {
            return getArtistById(artist.getArtistID());
        } else {
            throw new RuntimeException("No se pudo actualizar el artista con ID " + artist.getArtistID());
        }
    }

}
