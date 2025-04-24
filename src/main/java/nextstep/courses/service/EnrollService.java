package nextstep.courses.service;

import nextstep.courses.domain.Participant;
import nextstep.courses.domain.ParticipantRepository;
import nextstep.courses.domain.Participants;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.transaction.annotation.Transactional;

public class EnrollService {

    private final SessionRepository sessionRepository;

    private final ParticipantRepository participantRepository;

    public EnrollService(SessionRepository sessionRepository, ParticipantRepository participantRepository) {
        this.sessionRepository = sessionRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional
    public void enroll(Long sessionId, Long userId, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // service param에는 안 넣음, 조회
        Participants participants = participantRepository.findBySessionId(sessionId);

        Participant enrolledParticipant = session.enroll(userId, payment, participants);
        participantRepository.save(sessionId, enrolledParticipant);
    }
}
