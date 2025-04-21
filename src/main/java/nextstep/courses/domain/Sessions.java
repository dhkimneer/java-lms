package nextstep.courses.domain;

import java.util.HashMap;
import java.util.Map;

public class Sessions {

    private final Map<Integer, Session> sessionsByTerm = new HashMap<>();

    public synchronized void add(Session session) {
        int nextTerm = sessionsByTerm.size() + 1;
        sessionsByTerm.put(nextTerm, session);
    }

    public int size() {
        return sessionsByTerm.size();
    }

    public Session getByTerm(int term) {
        return sessionsByTerm.get(term);
    }
}
