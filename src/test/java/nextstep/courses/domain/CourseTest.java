package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CourseTest {

    @Test
    @DisplayName("Course 과정 추가")
    void addCourse() {
        // given
        Course course = new Course();
        Session session = SessionTest.DEFAULT_SESSION;

        // when
        course.addSession(session);

        // then
        assertThat(course.getSessionByTerm(1)).isEqualTo(session);
    }
}
