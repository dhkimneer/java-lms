package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageCover;
import nextstep.courses.domain.ImageCoverRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository("imageCoverRepository")
public class JdbcImageCoverRepository implements ImageCoverRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcImageCoverRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ImageCover save(ImageCover imageCover) {

        String sql = "insert into image_cover (url, size, type, width, height, session_id) values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, imageCover.getUrl());
            ps.setInt(2, imageCover.getSize());
            ps.setString(3, imageCover.getType().name());
            ps.setInt(4, imageCover.getWidth());
            ps.setInt(5, imageCover.getHeight());
            ps.setLong(6, imageCover.getSessionId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return findById(generatedId).orElseThrow(() ->
                new IllegalArgumentException("이미지를 찾을 수 없습니다."));
    }

    @Override
    public Optional<ImageCover> findById(Long id) {

        String sql = "select id, url, size, type, width, height, session_id from image_cover where id = ?";
        RowMapper<ImageCover> rowMapper = (rs, rowNum) -> new ImageCover(
                rs.getLong("id"),
                rs.getInt("size"),
                rs.getString("url"),
                rs.getInt("width"),
                rs.getInt("height"),
                rs.getLong("session_id")
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Optional<ImageCover> findBySessionId(Long sessionId) {

        String sql = "select id, url, size, type, width, height, session_id from image_cover where session_id = ?";
        RowMapper<ImageCover> rowMapper = (rs, rowNum) -> new ImageCover(
                rs.getLong("id"),
                rs.getInt("size"),
                rs.getString("url"),
                rs.getInt("width"),
                rs.getInt("height"),
                rs.getLong("session_id")
        );
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, sessionId));
    }
}
