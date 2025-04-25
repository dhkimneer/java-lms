package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {

    private final MaxCapacity maxCapacity; // 최대 수강 인원

    private final TuitionFee tuitionFee; // 수강료

    // 필수 불변 필드만 넣는다.
    public PaidSession(Long id, Period period,
                       SessionStatus sessionStatus,
                       EnrollmentStatus enrollmentStatus,
                       MaxCapacity maxCapacity, TuitionFee tuitionFee) {
        super(id, period, sessionStatus, enrollmentStatus);
        validate(maxCapacity, tuitionFee); // null check
        this.maxCapacity = maxCapacity;
        this.tuitionFee = tuitionFee;
    }

    public PaidSession(Period period,
                       MaxCapacity maxCapacity, TuitionFee tuitionFee) {
        this(null, period, SessionStatus.PREPARING,
                EnrollmentStatus.NON_RECRUITING, maxCapacity, tuitionFee);
    }

    private void validate(MaxCapacity maxCapacity, TuitionFee tuitionFee) {
        if (maxCapacity == null || tuitionFee == null) {
            throw new IllegalArgumentException("MaxCapacity and TuitionFee cannot be null");
        }
    }

    @Override
    protected void validateEnrollCondition(Payment payment, int participantSize) {
        if (!payment.isSameAmount(tuitionFee)) {
            throw new IllegalArgumentException("가격이 일치하지 않습니다.");
        }

        maxCapacity.validateAccomodation(participantSize + 1); // 검증 포인트!!
    }

    public MaxCapacity getMaxCapacity() {
        return maxCapacity;
    }

    public TuitionFee getTuitionFee() {
        return tuitionFee;
    }
}
