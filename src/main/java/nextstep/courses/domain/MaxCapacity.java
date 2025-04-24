package nextstep.courses.domain;

import java.util.Objects;

public class MaxCapacity {

    private final Integer maxCapacity;

    public MaxCapacity(Integer maxCapacity) {

        validateMaxCapacity(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    private void validateMaxCapacity(Integer maxCapacity) {
        if (maxCapacity == null || maxCapacity < 1) {
            throw new IllegalArgumentException("maxCapacity must be greater than one");
        }
    }

    public void validateAccomodation(int participantSize) {
        if (maxCapacity < participantSize) {
            throw new IllegalArgumentException("maxCapacity must be greater than participantSize");
        }
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaxCapacity that = (MaxCapacity) o;
        return Objects.equals(maxCapacity, that.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxCapacity);
    }
}
