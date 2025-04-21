package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;
import java.util.List;

public abstract class Session {

    private Period period; // 시작, 종료일 정보

    private ImageCover imageCover; // 이미지 정보

    private SessionStatus sessionStatus = SessionStatus.PREPARING; // 강의 상태

    private List<Long> participants = new ArrayList<>();

    public Session(Period period, ImageCover imageCover) {
        validate(period, imageCover); // period, imageCover 자체에 대한 것보다, 필수값 검증
        this.period = period;
        this.imageCover = imageCover;
    }

    private void validate(Period period, ImageCover imageCover) {
        if (period == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }

        if (imageCover == null) {
            throw new IllegalArgumentException("ImageCover cannot be null");
        }
    }

    public void enroll(Long userId, Payment payment) {
        validateSessionStatus();
        validateEnrollCondition(payment);
        participants.add(userId);
    }

    private void validateSessionStatus() {
        if (!SessionStatus.ENROLLING.equals(this.sessionStatus)) {
            throw new IllegalStateException("Session is not enrolling.");
        }
    }

    public void openEnrollment() {
        this.sessionStatus = SessionStatus.ENROLLING;
    }

    public void closeEnrollment() {
        this.sessionStatus = SessionStatus.CLOSED;
    }

    public boolean isPreparing() {
        return sessionStatus.equals(SessionStatus.PREPARING);
    }

    public boolean isParticipant(long userId) {
        return participants.contains(userId);
    }

    public int getParticipantSize() {
        return participants.size();
    }

    protected abstract void validateEnrollCondition(Payment payment);
}
