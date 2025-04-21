package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TuitionFeeTest {

    @Test
    @DisplayName("수업료가 null이거나 0 이하이면 안 된다.")
    void tuitionFeeMustNotBeNullOrZero() {

        assertThrows(IllegalArgumentException.class, () -> {
            new TuitionFee(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TuitionFee(0L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TuitionFee(-1L);
        });
    }

    @Test
    @DisplayName("수업료와 실제 수강생 결제 금액은 일치하여야 한다.")
    void tuitionFeeMustBeEqualToPaidAmount() {
        TuitionFee tuitionFee = new TuitionFee(50000L);

        assertThat(tuitionFee.matches(50000L)).isTrue();
    }
}