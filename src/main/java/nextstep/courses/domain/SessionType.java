package nextstep.courses.domain;

public enum SessionType {

    FREE,

    PAID;

    // DB에 insert하기 위함
    public static SessionType from(Session session) {
        if (session instanceof FreeSession) return FREE;
        if (session instanceof PaidSession) return PAID;
        throw new IllegalArgumentException("알 수 없는 강의 유형입니다: " + session.getClass());
    }
}