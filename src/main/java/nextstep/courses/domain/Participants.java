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

    public void add(Participant participant) {
        if (contains(participant.getUserId())) {
            throw new IllegalArgumentException("이미 등록된 사용자입니다.");
        }
        participants.add(participant);
    }

    public boolean contains(Long userId) {
        return participants.stream().anyMatch(p -> p.getUserId().equals(userId));
    }

    public int size() {
        return participants.size();
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }
}
