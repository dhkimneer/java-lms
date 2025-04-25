package nextstep.courses.domain;

import java.util.Objects;

public class Participant { // 원시 객체, 다만 정보 추가 시 사용(단순 매핑 테이블 아님, 엔티티로 봐)

    private final Long sessionId;

    private final Long userId;

    private final ApprovalStatus approvalStatus;

    public Participant(Long sessionId, Long userId, ApprovalStatus approvalStatus) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.approvalStatus = approvalStatus;
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
        return ApprovalStatus.APPROVED.equals(approvalStatus);
    }
}
