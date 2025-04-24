package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;
import nextstep.courses.domain.Participants;
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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcParticipantRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ParticipantRepository participantRepository;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        participantRepository = new JdbcParticipantRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("단 건 저장")
    void save() {
        // given
        FreeSession session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now())
        );
        Session savedSession = sessionRepository.save(session);
        Long sessionId = savedSession.getId();

        Participant participant = new Participant(sessionId, 1L);
        int count = participantRepository.save(sessionId, participant);

        // when & then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("session_id로 조회")
    void findBySessionId() {
        // given
        FreeSession session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now())
        );
        Session savedSession = sessionRepository.save(session);
        Long sessionId = savedSession.getId();

        // when
        Participant participant1 = new Participant(sessionId, 1L);
        Participant participant2 = new Participant(sessionId, 2L);
        participantRepository.save(sessionId, participant1);
        participantRepository.save(sessionId, participant2);

        Participants foundParticipants = participantRepository.findBySessionId(sessionId);

        // then
        assertThat(foundParticipants.size()).isEqualTo(2);
        assertThat(foundParticipants.getParticipants()).containsExactlyInAnyOrder(participant1, participant2);
    }
}