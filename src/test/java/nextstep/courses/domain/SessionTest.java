package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    @DisplayName("강의 초기 상태는 준비 중/비 모집 중이다.")
    void sessionStatusInitIsPreparing() {
        assertThat(DEFAULT_SESSION.isPreparing()).isTrue();
        assertThat(DEFAULT_SESSION.isNonRecruiting()).isTrue();
    }

    @Test
    @DisplayName("강의 등록 시 '비 모집 중일 경우' 등록은 불가능하다.")
    void cannotValidateStatusAndConditionWhenNonRecruiting() {

        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1L))
        );

        Participants participants = new Participants(
                List.of(new Participant(session.getId(), 1L, ApprovalStatus.PENDING))
        );

        assertThrows(IllegalStateException.class, () -> {
            session.validateStatusAndCondition(new Payment("1", 0L, 1L, session.getId()), participants.size());
        });
    }

    @Test
    @DisplayName("강의 등록 시 '강의가 종료되었을 경우' 등록은 불가능하다.")
    void cannotValidateStatusAndConditionWhenSessionIsClosed() {

        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1L))
        );

        Participants participants = new Participants(
                List.of(new Participant(session.getId(), 1L, ApprovalStatus.PENDING))
        );

        session.openEnrollment();
        session.close(); // 모집 중, but 종료

        assertThrows(IllegalStateException.class, () -> {
            session.validateStatusAndCondition(new Payment("1", 0L, 1L, session.getId()), participants.size());
        });
    }

    @Test
    @DisplayName("강의 등록 가능")
    void canValidateStatusAndCondition() {

        Session session = new FreeSession(
                new Period(LocalDate.now(), LocalDate.now().plusDays(1L))
        );

        Participants participants = new Participants(
                List.of(new Participant(session.getId(), 1L, ApprovalStatus.PENDING))
        );

        session.openEnrollment();
        session.startRecruiting();

        assertDoesNotThrow(() -> session.validateStatusAndCondition(
                new Payment("1", 0L, 1L, session.getId()), participants.size())
        );
    }
}
