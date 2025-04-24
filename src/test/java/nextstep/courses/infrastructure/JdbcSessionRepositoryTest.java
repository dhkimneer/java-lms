package nextstep.courses.infrastructure;

import nextstep.courses.domain.MaxCapacity;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.TuitionFee;
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
class JdbcSessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("강의 저장")
    void save() {
        // given
        PaidSession session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now()),
                new MaxCapacity(3),
                new TuitionFee(50000L)
        );

        // when
        Session savedSession = sessionRepository.save(session);

        // then
        assertThat(savedSession.getId()).isNotNull();
        assertThat(savedSession.getPeriod()).isEqualTo(session.getPeriod());
        assertThat(savedSession.getSessionStatus()).isEqualTo(SessionStatus.PREPARING);

        assertThat(savedSession).isInstanceOf(PaidSession.class);
        PaidSession paid = (PaidSession) savedSession;

        assertThat(paid.getMaxCapacity()).isEqualTo(session.getMaxCapacity());
        assertThat(paid.getTuitionFee()).isEqualTo(session.getTuitionFee());
    }

    @Test
    @DisplayName("강의 조회")
    void findById() {
        // given
        PaidSession session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now()),
                new MaxCapacity(3),
                new TuitionFee(50000L)
        );

        // when
        Session savedSession = sessionRepository.save(session);
        Optional<Session> foundSession = sessionRepository.findById(savedSession.getId());

        // then
        assertThat(foundSession.isPresent()).isTrue();
        assertThat(foundSession.get().getId()).isEqualTo(savedSession.getId());
    }
}