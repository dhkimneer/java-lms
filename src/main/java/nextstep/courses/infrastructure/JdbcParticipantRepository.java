package nextstep.courses.infrastructure;

import nextstep.courses.domain.ApprovalStatus;
import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;
import nextstep.courses.domain.Participants;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository("participantRepository")
public class JdbcParticipantRepository implements ParticipantRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcParticipantRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Long sessionId, Participant participant) {

        String sql = "insert into participant(session_id, user_id) values (?, ?)";
        return jdbcTemplate.update(sql, sessionId, participant.getUserId());
    }

    @Override
    public void saveAll(Long sessionId, Participants participants) {

        String sql = "insert into participant(session_id, user_id) values (?, ?)";
        jdbcTemplate.batchUpdate(sql,
                participants.getParticipants()
                        .stream()
                        .map(participant -> new Object[] {sessionId, participant.getUserId()})
                        .collect(Collectors.toList()));
    }

    @Override
    public Participants findBySessionId(Long sessionId) {

        String sql = "select session_id, user_id from participant where session_id = ?";

        List<Participant> results = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Participant(
                        rs.getLong("session_id"),
                        rs.getLong("user_id"),
                        ApprovalStatus.valueOf(rs.getString("approval_status"))
                ),
                sessionId
        );

        return new Participants(results);
    }
}
