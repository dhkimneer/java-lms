package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionTest {

    public static final Session DEFAULT_SESSION = new FreeSession(
            new Period(LocalDate.now(), LocalDate.now().plusDays(1L))
    );

    @Test
    @DisplayName("기간은 null일 수 없습니다.")
    void periodOrImageCoverMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new FreeSession(null));
    }

    @Test
    @DisplayName("강의 초기 상태는 준비 중이다.")
    void sessionStatusInitIsPreparing() {
        assertThat(DEFAULT_SESSION.isPreparing()).isTrue();
    }

    @Test
    @DisplayName("강의 등록 시 '모집 중'이 아닐 경우 등록은 불가능하다.")
    void cannotEnrollWhenSessionStatusIsNotEnrolling() {

        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1L))
        );

        Participants participants = new Participants(
                List.of(new Participant(session.getId(), 1L))
        );

        assertThrows(IllegalStateException.class, () -> {
            session.enroll(1L, new Payment("1", 0L, 1L, session.getId()), participants);
        });

        session.openEnrollment();
        Participant enrolledParticipant = session.enroll(1L, new Payment("1", 0L, 1L, session.getId()), participants);
        assertThat(enrolledParticipant.getUserId()).isEqualTo(1L);
    }
}
