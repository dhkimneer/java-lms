package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public abstract class Session {

    private Long id;

    private Period period; // 시작, 종료일 정보

    private SessionStatus sessionStatus = SessionStatus.PREPARING; // 강의 상태

    // type은 도메인에서는 없어도 됨 (다형성으로 처리 가능하기 때문에)

    public Session(Long id, Period period, SessionStatus sessionStatus) {
        validate(period); // period 필수값 검증
        this.id = id;
        this.period = period;
        this.sessionStatus = sessionStatus;
    }

    public Session(Period period) {
        this(null, period, SessionStatus.PREPARING);
    }

    private void validate(Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }
    }

    public Participant enroll(Long userId, Payment payment, Participants participants) {
        validateSessionStatus();
        validateEnrollCondition(payment, participants.size());

        Participant participant = new Participant(this.id, userId);
        participants.add(participant);

        return participant;
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

    public Long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    protected abstract void validateEnrollCondition(Payment payment, int participantSize);
}
