package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.ImageCover;
import nextstep.courses.domain.ImageCoverRepository;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcImageCoverRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageCoverRepository imageCoverRepository;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        imageCoverRepository = new JdbcImageCoverRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("이미지 저장")
    void save() {
        // given
        FreeSession session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now())
        );
        Session savedSession = sessionRepository.save(session);

        ImageCover imageCover = new ImageCover(
                1024 * 1024,
                "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                300,
                200,
                savedSession.getId());

        // when
        ImageCover savedImageCover = imageCoverRepository.save(imageCover);

        // then
        assertThat(savedImageCover.getId()).isNotNull();
        assertThat(savedImageCover.getUrl()).isEqualTo(imageCover.getUrl());
        assertThat(savedImageCover.getSize()).isEqualTo(imageCover.getSize());
        assertThat(savedImageCover.getType()).isEqualTo(imageCover.getType());
        assertThat(savedImageCover.getWidth()).isEqualTo(imageCover.getWidth());
        assertThat(savedImageCover.getHeight()).isEqualTo(imageCover.getHeight());
        assertThat(savedImageCover.getSessionId()).isEqualTo(imageCover.getSessionId());
    }

    @Test
    @DisplayName("id로 조회")
    void findById() {
        // given
        FreeSession session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now())
        );
        Session savedSession = sessionRepository.save(session);

        ImageCover imageCover = new ImageCover(
                1024 * 1024,
                "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                300,
                200,
                savedSession.getId());

        // when
        ImageCover savedImageCover = imageCoverRepository.save(imageCover);
        Optional<ImageCover> foundImageCover = imageCoverRepository.findById(savedImageCover.getId());

        // then
        assertThat(foundImageCover).isEqualTo(Optional.of(savedImageCover));
    }

    @Test
    @DisplayName("session_id로 조회")
    void findBySessionId() {
        // given
        FreeSession session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now())
        );
        Session savedSession = sessionRepository.save(session);

        ImageCover imageCover = new ImageCover(
                1024 * 1024,
                "https://d25nznepghf50.cloudfront.net/practice/105/144/3/337f46dc-0d46-4213-9ffe-3cfbc94c15c2.png",
                300,
                200,
                savedSession.getId());

        // when
        ImageCover savedImageCover = imageCoverRepository.save(imageCover);
        Optional<ImageCover> foundImageCover = imageCoverRepository.findBySessionId(savedImageCover.getSessionId());

        // then
        assertThat(foundImageCover).isEqualTo(Optional.of(savedImageCover));
    }
}