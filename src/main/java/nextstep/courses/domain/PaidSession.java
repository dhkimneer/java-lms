package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {

    private final MaxCapacity maxCapacity; // 최대 수강 인원

    private final TuitionFee tuitionFee; // 수강료

    // 필수 불변 필드만 넣는다.
    public PaidSession(Period period, ImageCover imageCover,
                       MaxCapacity maxCapacity, TuitionFee tuitionFee) {
        super(period, imageCover);
        this.maxCapacity = maxCapacity;
        this.tuitionFee = tuitionFee;
    }

    @Override
    protected void validateEnrollCondition(Payment payment) {
        if (!payment.isSameAmount(tuitionFee)) {
            throw new IllegalArgumentException("가격이 일치하지 않습니다.");
        }

        maxCapacity.validateAccomodation(getParticipantSize() + 1); // 검증 포인트!!
    }
}
