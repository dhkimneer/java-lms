package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class PaidSessionTest {

    @Test
    @DisplayName("수업료와 실제 수강 금액은 일치하여야 한다.")
    void tuitionFeeAndPaidAmountMustBeEqual() {
        Session session = new PaidSession(
                new Period(LocalDate.now(), LocalDate.now()),
                new MaxCapacity(1),
                new TuitionFee(50000L)
        );

        Participants participants = new Participants(
                List.of(new Participant(session.getId(), 1L))
        );

        session.openEnrollment();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            session.enroll(1L, new Payment("1L", 30000L, 1L, session.getId()), participants);
        });
    }
}