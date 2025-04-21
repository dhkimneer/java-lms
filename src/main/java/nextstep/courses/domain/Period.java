package nextstep.courses.domain;

import java.time.LocalDate;

public class Period {

    private final LocalDate startTime; // 시작일

    private final LocalDate endTime; // 종료일

    public Period(LocalDate startTime, LocalDate endTime) {
        validate(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validate(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("startTime and endTime cannot be null");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("startTime cannot be after endTime");
        }
    }
}
