package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PeriodTest {

    @Test
    @DisplayName("시작, 종료 시간은 null이면 안 된다.")
    void startTimeAndEndTimeShouldNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(null, LocalDate.now());
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Period(LocalDate.now(), null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Period(null, null);
        });
    }

    @Test
    @DisplayName("시작 시간은 반드시 종료 시간 이후여야 한다.")
    void startTimeMustNotBeAfterThanEndTime() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Period(LocalDate.now().plusDays(1), LocalDate.now());
        });
    }
}