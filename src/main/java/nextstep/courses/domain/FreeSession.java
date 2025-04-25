package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Long id, Period period, SessionStatus sessionStatus,
                       EnrollmentStatus enrollmentStatus) {
        super(id, period, sessionStatus, enrollmentStatus);
    }

    public FreeSession(Period period) {
        super(period);
    }

    /**
     * 공짜라서 등록 조건을 따로 validate할 필요가 없음
     * @param payment
     * @param participantSize
     */
    @Override
    protected void validateEnrollCondition(Payment payment, int participantSize) {

    }
}
