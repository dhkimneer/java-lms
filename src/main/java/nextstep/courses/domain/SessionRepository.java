package nextstep.courses.domain;

import java.util.Optional;

public interface SessionRepository {

    Session save(Session session);

    Optional<Session> findById(Long id);
}
