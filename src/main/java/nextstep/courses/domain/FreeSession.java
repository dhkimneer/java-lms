package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Period period, ImageCover imageCover) {
        super(period, imageCover);
    }

    /**
     * 공짜라서 등록 조건을 따로 validate할 필요가 없음
     * @param payment
     */
    @Override
    protected void validateEnrollCondition(Payment payment) {

    }
}
