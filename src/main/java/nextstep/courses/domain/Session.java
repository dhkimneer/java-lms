package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public abstract class Session {

    private Long id;

    private Period period; // 시작, 종료일 정보

    private SessionStatus sessionStatus = SessionStatus.PREPARING; // 강의 상태

    private EnrollmentStatus enrollmentStatus; // 모집 상태

    // type은 도메인에서는 없어도 됨 (다형성으로 처리 가능하기 때문에)

    public Session(Long id, Period period, SessionStatus sessionStatus, EnrollmentStatus enrollmentStatus) {
        validate(period); // period 필수값 검증
        this.id = id;
        this.period = period;
        this.sessionStatus = sessionStatus;
        this.enrollmentStatus = enrollmentStatus;
    }

    public Session(Period period) {
        this(null, period, SessionStatus.PREPARING, EnrollmentStatus.NON_RECRUITING);
    }

    private void validate(Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }
    }

    public void validateStatusAndCondition(Payment payment, int participantSize) {
        validateStatus();
        validateEnrollCondition(payment, participantSize);
    }

    private void validateStatus() {
        if (!EnrollmentStatus.RECRUITING.equals(this.enrollmentStatus) || SessionStatus.CLOSED.equals(this.sessionStatus)) {
            throw new IllegalStateException("수강 신청이 불가능한 상태입니다.");
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

    public boolean isNonRecruiting() { return EnrollmentStatus.NON_RECRUITING.equals(this.enrollmentStatus); }

    public Long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    protected abstract void validateEnrollCondition(Payment payment, int participantSize);

    public void close() {
        this.sessionStatus = SessionStatus.CLOSED;
    }

    public void startRecruiting() {
        this.enrollmentStatus = EnrollmentStatus.RECRUITING;
    }
}
