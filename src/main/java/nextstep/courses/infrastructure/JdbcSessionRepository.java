package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.MaxCapacity;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Period;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.TuitionFee;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Session save(Session session) {

        String sql = "insert into session (start_time, end_time, status, type, max_capacity, tuition_fee) values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });

            ps.setDate(1, Date.valueOf(session.getPeriod().getStartTime()));
            ps.setDate(2, Date.valueOf(session.getPeriod().getEndTime()));
            ps.setString(3, session.getSessionStatus().name());

            ps.setString(4, SessionType.from(session).name());
            if (session instanceof PaidSession) {
                ps.setInt(5, ((PaidSession) session).getMaxCapacity().getMaxCapacity());
                ps.setLong(6, ((PaidSession) session).getTuitionFee().getTuitionFee());
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.BIGINT);
            }

            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return findById(generatedId).orElseThrow(() ->
                new IllegalArgumentException("강의를 찾을 수 없습니다."));
    }

    @Override
    public Optional<Session> findById(Long id) {

        String sql = "select id, start_time, end_time, status, type, max_capacity, tuition_fee from session where id = ?";

        List<Session> result = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long sessionId = rs.getLong("id");

            LocalDate start = rs.getDate("start_time").toLocalDate();
            LocalDate end = rs.getDate("end_time").toLocalDate();
            Period period = new Period(start, end);

            SessionStatus status = SessionStatus.valueOf(rs.getString("status"));
            SessionType type = SessionType.valueOf(rs.getString("type"));

            if (type == SessionType.FREE) {
                return new FreeSession(sessionId, period, status);
            }

            if (type == SessionType.PAID) {
                MaxCapacity maxCapacity = new MaxCapacity(rs.getInt("max_capacity"));
                TuitionFee tuitionFee = new TuitionFee(rs.getLong("tuition_fee"));
                return new PaidSession(sessionId, period, status, maxCapacity, tuitionFee);
            }

            throw new IllegalArgumentException("알 수 없는 강의 유형 : " + type);
        }, id);

        return result.stream().findFirst();
    }
}
