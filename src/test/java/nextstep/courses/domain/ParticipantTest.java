package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticipantTest {

    @Test
    @DisplayName("승인")
    void approve() {
        // given
        Participant participant = new Participant(1L, 1L, ApprovalStatus.PENDING);

        // when
        participant.approve();

        // then
        assertThat(participant.isApproved()).isTrue();
    }

    @Test
    @DisplayName("대기 중일 시, 취소 불가")
    void disapproveException() {
        // given
        Participant participant = new Participant(1L, 1L, ApprovalStatus.PENDING);

        // when & then
        assertThrows(IllegalStateException.class, participant::disapprove);
    }

    @Test
    @DisplayName("대기 중이 아닐 시, 취소 가능")
    void disapprove() {
        // given
        Participant participant = new Participant(1L, 1L, ApprovalStatus.PENDING);
        participant.approve();

        // when
        participant.disapprove();

        // then
        assertThat(participant.isDisapproved()).isTrue();
    }
}