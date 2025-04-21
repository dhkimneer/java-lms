package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxCapacityTest {

    @Test
    @DisplayName("최대 수용 인원은 최소 1명 이상이어야 한다.")
    void maxCapacityMustBeGreaterThanOne() {

        assertThrows(IllegalArgumentException.class, () -> {
            new MaxCapacity(0);
        });
    }

    @Test
    @DisplayName("최대 수용 인원은 최소 참가자 수 이상이어야 하며, 아닐 경우 예외가 터진다.")
    void maxCapacityMustBeGreaterOrEqualThanParticipantSize() {

        // given
        MaxCapacity maxCapacity = new MaxCapacity(4);
        int participantSize = 5;

        // when&then
        assertThrows(IllegalArgumentException.class, () -> {
            maxCapacity.validateAccomodation(participantSize);
        });
    }
}