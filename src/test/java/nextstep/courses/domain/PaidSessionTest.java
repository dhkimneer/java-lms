package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static nextstep.courses.domain.ImageCoverTest.DEFAULT_IMAGE_COVER;

class PaidSessionTest {

    @Test
    @DisplayName("수업료와 실제 수강 금액은 일치하여야 한다.")
    void tuitionFeeAndPaidAmountMustBeEqual() {
        Session session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now()),
                DEFAULT_IMAGE_COVER,
                new MaxCapacity(1),
                new TuitionFee(50000L)
        );

        session.openEnrollment();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.enroll(1L, new Payment("1L", 30000L, 1L, 1L));
        });
    }

    @Test
    @DisplayName("최대 수강 인원은 수강생 숫자보다 크거나 같아야 한다.")
    void validateAccomodation() {
        Session session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now()),
                DEFAULT_IMAGE_COVER,
                new MaxCapacity(1),
                new TuitionFee(50000L)
        );

        session.openEnrollment();
        session.enroll(1L, new Payment("1L", 50000L, 1L, 1L));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.enroll(2L, new Payment("2L", 50000L, 2L, 2L));
        });
    }
}