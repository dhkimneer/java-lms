package nextstep.payments.domain;

import nextstep.courses.domain.TuitionFee;

public class Payment {

    private final String id; //

    private final Long paidAmount; // 결제 금액

    private final Long nsUserId; // 결제 주체

    private final Long sessionId; // 강의 정보

    public Payment(String id, Long paidAmount, Long nsUserId, Long sessionId) {
        this.id = id;
        this.paidAmount = paidAmount;
        this.nsUserId = nsUserId;
        this.sessionId = sessionId;
    }

    public boolean isSameAmount(TuitionFee tuitionFee) {
        return tuitionFee.matches(paidAmount);
    }
}
