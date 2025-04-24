package nextstep.courses.domain;

public interface ParticipantRepository {

    int save(Long sessionId, Participant participant);

    void saveAll(Long sessionId, Participants participants);

    Participants findBySessionId(Long sessionId);
}
