package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Participants {

    private final List<Participant> participants;

    // 방어적 복사
    public Participants(List<Participant> participants) {
        this.participants = new ArrayList<>(participants);
    }

    public Participant createAndAdd(Long sessionId, Long userId) {
        if (contains(sessionId, userId)) {
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");
        }

        Participant participant = new Participant(sessionId, userId, ApprovalStatus.PENDING);
        participants.add(participant);

        return participant;
    }

    public boolean contains(Long sessionId, Long userId) {
        return participants.stream()
                .anyMatch(p -> p.getSessionId().equals(sessionId) &&
                        p.getUserId().equals(userId));
    }

    public int size() {
        return participants.size();
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }
}
