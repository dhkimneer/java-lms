package nextstep.courses.service;

import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;

public class EnrollService {

    private final SessionRepository sessionRepository;

    public EnrollService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void enroll(Long sessionId, Long userId, Payment payment) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"))
                .enroll(userId, payment);
    }
}
