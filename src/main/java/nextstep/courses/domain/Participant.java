package nextstep.courses.domain;

import java.util.Objects;

public class Participant {

    private final Long sessionId;

    private final Long userId;

    private ApprovalStatus approvalStatus;

    public Participant(Long sessionId, Long userId, ApprovalStatus approvalStatus) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.approvalStatus = approvalStatus;
    }

    public void approve() {
        this.approvalStatus = ApprovalStatus.APPROVED;
    }

    public void disapprove() {
        if (ApprovalStatus.PENDING.equals(this.approvalStatus)) {
            throw new IllegalStateException("대기 중인 자는 취소가 불가능합니다.");
        }

        this.approvalStatus = ApprovalStatus.DISAPPROVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public boolean isApproved() {
        return ApprovalStatus.APPROVED.equals(this.approvalStatus);
    }

    public boolean isDisapproved() {
        return ApprovalStatus.DISAPPROVED.equals(this.approvalStatus);
    }
}
