package AndisUT2.ArtistAPI.repository.command;

import AndisUT2.ArtistAPI.dto.DTOAlbumCommand;
import AndisUT2.ArtistAPI.model.Album;
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
public class AlbumRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Album> albumRowMapper = (rs, rowNum) -> {
        Album album = new Album();
        album.setAlbumId(rs.getInt("album_id"));
        album.setAlbumName(rs.getString("album_name"));
        album.setArtistId(rs.getInt("artist_id"));
        return album;
    };

    public AlbumRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public Album getAlbumByName(String name) {
        String sql = "select * from album where album_name = ?";
        try{
            return jdbcTemplate.queryForObject(sql, albumRowMapper, name);
        }
        catch(EmptyResultDataAccessException e){
            throw new RuntimeException("No se encontro album con el nombre " + name);
        }
    }

    public Album getAlbumById(int albumId) {
        String sql = "select * from album where album_id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, albumRowMapper, albumId);
        }
        catch(EmptyResultDataAccessException e){
            throw new RuntimeException("No se encontro album con el ID " + albumId);
        }
    }

    public List<Album> getAllAlbums() {
        String sql = "select * from album";
        return jdbcTemplate.query(sql, albumRowMapper);
    }

    public List<Album> getAlbumsByArtistId(int artistId) {
        String sql = "select * from album where artist_id = ?";
        return jdbcTemplate.query(sql, albumRowMapper, artistId);
    }

    public Album saveAlbum(Album album) {
        String sql = "INSERT INTO album (album_name, artist_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, album.getAlbumName());
            ps.setInt(2, album.getArtistId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            album.setAlbumId(key.intValue());
        } else {
            throw new RuntimeException("No se pudo obtener el ID generado del álbum");
        }

        return album;
    }

    public List<DTOAlbumCommand> getAllAlbumsWithArtists() {
        String sql = """
        SELECT 
            al.album_id AS album_id,
            al.album_name AS album_name,
            ar.artist_id AS artist_id,
            ar.name AS artist_name
        FROM album al
        JOIN artist ar ON al.artist_id = ar.artist_id
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DTOAlbumCommand dto = new DTOAlbumCommand();
            dto.setAlbumId(rs.getInt("album_id"));
            dto.setAlbumName(rs.getString("album_name"));
            dto.setArtistId(rs.getInt("artist_id"));
            dto.setArtistName(rs.getString("artist_name"));
            dto.setSongs(null);
            return dto;
        });
    }

    public DTOAlbumCommand getAlbumWithArtistById(int albumId) {
        String sql = """
        SELECT 
            al.album_id AS album_id,
            al.album_name AS album_name,
            ar.artist_id AS artist_id,
            ar.name AS artist_name
        FROM album al
        JOIN artist ar ON al.artist_id = ar.artist_id
        WHERE al.album_id = ?
    """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                DTOAlbumCommand dto = new DTOAlbumCommand();
                dto.setAlbumId(rs.getInt("album_id"));
                dto.setAlbumName(rs.getString("album_name"));
                dto.setArtistId(rs.getInt("artist_id"));
                dto.setArtistName(rs.getString("artist_name"));
                dto.setSongs(null);
                return dto;
            }, albumId);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No se encontro album con ID " + albumId);
        }
    }

    public Album updateAlbum(Album album) {
        String sql = "UPDATE album SET album_name = ?, artist_id = ? WHERE album_id = ?";
        int rows = jdbcTemplate.update(sql, album.getAlbumName(), album.getArtistId(), album.getAlbumId());

        if (rows > 0) {
            return getAlbumById(album.getAlbumId());
        } else {
            throw new RuntimeException("No se pudo actualizar el álbum con ID " + album.getAlbumId());
        }
    }

}
