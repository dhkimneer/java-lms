package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static nextstep.courses.domain.ImageCoverTest.DEFAULT_IMAGE_COVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionTest {

    public static final Session DEFAULT_SESSION = new FreeSession(
            new Period(LocalDate.now(), LocalDate.now().plusDays(1L)),
            DEFAULT_IMAGE_COVER
    );

    @Test
    @DisplayName("기간이나 이미지 정보는 null일 수 없습니다.")
    void periodOrImageCoverMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new FreeSession(null, null));

        assertThrows(IllegalArgumentException.class, () ->
                new FreeSession(new Period(LocalDate.now(), LocalDate.now()), null));

        assertThrows(IllegalArgumentException.class, () ->
                new FreeSession(null, DEFAULT_IMAGE_COVER));
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
                new Period(LocalDate.now(), LocalDate.now().plusDays(1L)),
                DEFAULT_IMAGE_COVER
        );

        assertThrows(IllegalStateException.class, () -> {
            session.enroll(1L, new Payment("1", 0L, 1L, 1L));
        });

        session.openEnrollment();
        session.enroll(1L, new Payment("1", 0L, 1L, 1L));
        assertThat(session.isParticipant(1L)).isTrue();
    }
}
