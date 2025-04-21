package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionsTest {

    @Test
    @DisplayName("기수 잘 들어가는지 확인")
    void sessionAddTest() {
        Sessions sessions = new Sessions();
        sessions.add(SessionTest.DEFAULT_SESSION);

        assertThat(sessions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기수 조회 테스트")
    void sessionTermTest() {
        Sessions sessions = new Sessions();
        Session session1 = SessionTest.DEFAULT_SESSION;
        sessions.add(session1);

        assertThat(sessions.getByTerm(1)).isEqualTo(session1);
    }

    @Test
    @DisplayName("기수 조회 null (아무 것도 없을 시)")
    void sessionTermNullTest() {
        Sessions sessions = new Sessions();

        assertThat(sessions.getByTerm(1)).isNull();
    }
}