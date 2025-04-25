package nextstep.courses.domain;

import java.util.Optional;

public interface ParticipantRepository {

    int save(Long sessionId, Participant participant);

    void saveAll(Long sessionId, Participants participants);

    Participants findBySessionId(Long sessionId);

    Optional<Participant> findBySessionIdAndUserId(Long sessionId, Long userId);
}
