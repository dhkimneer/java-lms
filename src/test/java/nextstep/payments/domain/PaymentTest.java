package nextstep.payments.domain;

import nextstep.courses.domain.TuitionFee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @Test
    @DisplayName("수강료와 결제 금액은 일치하여야 한다.")
    void tuitionFeeMustBeEqualToPaidAmount() {
        Payment payment = new Payment("1", 50000L, 1L, 1L);

        assertThat(payment.isSameAmount(new TuitionFee(50000L))).isTrue();
    }
}